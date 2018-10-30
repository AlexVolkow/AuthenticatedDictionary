package crypto.skiplist

import crypto.hash.toBytes
import java.util.*
import kotlin.collections.ArrayList

class SkipList<E : Comparable<E>> : CryptoSet<E> {
    private val random = Random(901)
    private val towerBuilder: () -> Boolean = { if (isRandom) random.nextBoolean() else --height > 0 }
    private var root: SkipListNode<E>
    private var tail: SkipListNode<E>
    private var size: Int = 0

    private var height = 0
    private var isRandom: Boolean = true

    init {
        size = 0
        tail = SkipListNode(null)
        root = SkipListNode(null, right = tail)
        root.hash()
    }

    override fun size(): Int = size

    override fun insert(element: E, height: Int): Boolean {
        this.height = height
        isRandom = false
        return insert(element).also { isRandom = true }
    }

    /**
     * Adds e to the skiplist.
     * Returns false if already in skiplist, true otherwise
     */
    override fun insert(element: E): Boolean {
        val stack = findPath(element, requireEquals = false)

        if (stack.peek().right!!.value == element) {
            return false
        }

        size++
        val recalculateHashQueue = LinkedList<SkipListNode<E>>()
        var towerNode: SkipListNode<E>? = null

        do {
            val top = if (stack.isNotEmpty()) {
                stack.pop()
            } else {
                val newTail = SkipListNode(null, null, down = tail)
                tail.up = newTail
                tail = newTail
                tail.updateHash()
                val newRoot = SkipListNode(null, tail, down = root)
                root.up = newRoot
                root = newRoot
                root
            }

            val newNode = SkipListNode(element, top.right, towerNode)
            top.right = newNode
            towerNode?.up = newNode
            towerNode = newNode
            recalculateHashQueue.addLast(newNode)
            recalculateHashQueue.addLast(top)

            var prevNode = top
            while (stack.isNotEmpty() && stack.peek().right == prevNode) {
                prevNode = stack.pop()
                recalculateHashQueue.addLast(prevNode)
            }
        } while (towerBuilder())

        if (root.right != tail) {
            val newTail = SkipListNode(null, null, down = tail)
            tail.up = newTail
            tail = newTail
            tail.updateHash()
            val newRoot = SkipListNode(null, tail, down = root)
            root.up = newRoot
            root = newRoot
            recalculateHashQueue.addLast(root)
        }
        for (i in recalculateHashQueue) {
            i.updateHash()
        }

        while (stack.isNotEmpty()) {
            stack.pop().updateHash()
        }
        return true
    }

    override fun structureHash(): ByteArray {
        return root.hash()
    }

    override fun find(element: E): CryptoPath {
        val path = findPath(element, requireEquals = true)

        val v1 = path.pop()
        val isFound = v1.value == element

        val proof = ArrayList<ByteArray>()

        val w1 = v1.right!!

        if (w1.isPlateau()) {
            proof.add(w1.hash())
        } else {
            proof.add(w1.value.toBytes())
        }

        proof.add(v1.value.toBytes())

        var prevNode = v1
        while (path.size > 0) {
            val v = path.pop()
            val w = v.right!!
            if (w.isPlateau()) {
                if (w !== prevNode) {
                    proof.add(w.hash())
                } else {
                    if (v.isBase()) {
                        proof.add(v.value.toBytes())
                    } else {
                        val u = v.down!!
                        proof.add(u.hash())
                    }
                }
            }
            prevNode = v
        }

        return CryptoPath(isFound, proof)
    }

    override fun contains(element: E): Boolean {
        return findPath(element, requireEquals = false).peek().right!!.value == element
    }

    override fun remove(element: E): Boolean {
        val stack = findPath(element, requireEquals = false)

        if (stack.peek().right!!.value != element) {
            return false
        }
        size--
        while (stack.isNotEmpty()) {
            var curNode = stack.pop()
            if (curNode.right!!.right != null && curNode.right!!.value == element) {
                curNode.right = curNode.right!!.right
                curNode.updateHash()
            }
            while (stack.isNotEmpty() && stack.peek().right == curNode) {
                curNode = stack.pop()
                curNode.updateHash()
            }
        }

        while (root.down != null && root.down!!.right == tail.down) {
            root = root.down!!
            tail = tail.down!!
            root.updateHash()
        }
        return true
    }

    override fun toString(): String {
        return buildString {
            var down: SkipListNode<E>? = root
            while (down!!.down != null) {
                down = down.down
            }
            var right = down
            while (right != null) {
                append("${calcHeight(right)}: $right\n")
                right = right.right
            }
        }
    }

    private fun calcHeight(node: SkipListNode<E>): Int {
        var next: SkipListNode<E>? = node
        var count = 0
        while (next!!.up != null) {
            count++
            next = next.up
        }
        return count + 1
    }

    private fun findPath(element: E, requireEquals: Boolean): Stack<SkipListNode<E>> {
        val stack = Stack<SkipListNode<E>>()
        var node = root
        stack.push(node)

        do {
            while (node.right!!.right != null
                && (!requireEquals && node.right!!.value!! < element
                        || requireEquals && node.right!!.value!! <= element)
            ) {
                node = node.right!!
                stack.push(node)
            }
            if (node.down != null) {
                node = node.down!!
                stack.push(node)
            } else {
                break
            }
        } while (true)
        return stack
    }
}
