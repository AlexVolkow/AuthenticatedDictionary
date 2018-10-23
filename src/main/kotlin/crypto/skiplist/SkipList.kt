package crypto.skiplist

import crypto.hash.toBytes
import java.util.*
import kotlin.collections.ArrayList

class SkipList<E : Comparable<E>> : CryptoSet<E> {
    private val random = Random()

    private var root: SkipListNode<E>
    private var tail: SkipListNode<E>
    private var size: Int = 0

    init {
        size = 0
        tail = SkipListNode(null)
        root = SkipListNode(null, right = tail)
        root.updateHash()
    }

    fun getHead(): SkipListNode<E> {
        return root
    }

    override fun size(): Int = size

    /**
     * Adds e to the skiplist.
     * Returns false if already in skiplist, true otherwise
     */
    override fun insert(element: E): Boolean {
        val stack = findPath2(element)

        if (stack.peekFirst().right!!.value == element) {
            return false
        }

        size++
        val updateStack = LinkedList<SkipListNode<E>>()
        var towerNode: SkipListNode<E>? = null

        do {
            val top = if (stack.isNotEmpty()) {
                stack.pollFirst()
            } else {
                val newTail = SkipListNode(null, null, down = tail)
                tail.up = newTail
                tail = newTail
                tail.updateHash()
                val newRoot = SkipListNode(null, tail, down = root)
                root.up = newRoot
                root = newRoot
                updateStack.addLast(root)
                root
            }

            val newNode = SkipListNode(element, top.right, towerNode)
            top.right = newNode
            towerNode?.up = newNode
            towerNode = newNode

            //newNode.updateHash()
            //top.updateHash()

            updateStack.addLast(top)
            updateStack.addLast(newNode)

            var prevNode = top
            while (stack.isNotEmpty() && stack.peekFirst().right == prevNode) {
                prevNode = stack.pollFirst()
                //prevNode.updateHash()
                updateStack.addLast(prevNode)
            }
        } while (random.nextBoolean())

        if (root.right != tail) {
            val newTail = SkipListNode(null, null, down = tail)
            tail.up = newTail
            tail = newTail
            tail.updateHash()
            val newRoot = SkipListNode(null, tail, down = root)
            root.up = newRoot
            root = newRoot
            updateStack.addLast(root)
        }

        for (i in updateStack) {
            i.updateHash()
        }

        while (stack.isNotEmpty()) {
            stack.pollFirst().updateHash()
        }

        return true
    }

    private fun addLevel() {
        val newTail = SkipListNode(null, null, down = tail)
        tail.up = newTail
        tail = newTail
        tail.updateHash()
        val newRoot = SkipListNode(null, tail, down = root)
        root.up = newRoot
        root = newRoot
        root.updateHash()
    }

    override fun structureHash(): ByteArray {
        return root.hash()
    }

    override fun find(element: E): CryptoPath {
        val path = findPath(element)

        val v1 = path.pollFirst()
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
        while (path.isNotEmpty()) {
            val v = path.pollFirst()
            val w = v.right!!
            if (w.isPlateau()) {
                if (w != prevNode) {
                    proof.add(w.hash())
                } else {
                    if (v.isBase()) {
                        proof.add(v1.value.toBytes())
                    } else {
                        val u = v.down!!
                        proof.add(u.hash())
                    }
                }
            }
            prevNode = w
        }

        val z1 = v1.right!!
        return when {
            w1.isTower() -> CryptoPath(isFound, proof)
            w1.isPlateau() && z1.isTower() -> CryptoPath(
                    isFound,
                    listOf(z1.value.toBytes(), w1.value.toBytes()) + proof
            )
            w1.isPlateau() && z1.isPlateau() -> CryptoPath(isFound,
                    listOf(z1.hash(), w1.value.toBytes()) + proof
            )
            else -> throw IllegalStateException("SkipList in incorrect state")
        }
    }

    override fun contains(element: E): Boolean {
        return findPath2(element).peekFirst().right!!.value == element
    }

    override fun remove(element: E): Boolean {
        val stack = findPath2(element)

        if (stack.peekFirst().right!!.value != element) {
            return false
        }
        size--
        while (stack.isNotEmpty()) {
            var curNode = stack.pollFirst()
            if (curNode.right!!.right != null && curNode.right!!.value == element) {
                curNode.right = curNode.right!!.right
                curNode.updateHash()
            }
            while (stack.isNotEmpty() && stack.peekFirst().right == curNode) {
                curNode = stack.pollFirst()
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

    override fun iterator(): Iterator<E> {
        return SkipListIterator(this)
    }

    override fun toString(): String {
        var s = "SkipList: "
        for (o in this)
            s += o.toString() + ", "
        return s.substring(0, s.length - 2)
    }

    private fun findPath(element: E): Deque<SkipListNode<E>> {
        val stack = LinkedList<SkipListNode<E>>()
        var node = root

        do {
            // println(node.toString() + " " + node.right)
            while (node.right!!.right != null && node.right!!.value!! <= element) {
                stack.addFirst(node)
                node = node.right!!
            }
            stack.addFirst(node)
            if (node.down != null) {
                node = node.down!!
            } else {
                break
            }
        } while (true)
        return stack
    }
    private fun findPath2(element: E): Deque<SkipListNode<E>> {
        val stack = LinkedList<SkipListNode<E>>()
        var node = root

        do {
           // println(node.toString() + " " + node.right)
            while (node.right!!.right != null && node.right!!.value!! < element) {
                stack.addFirst(node)
                node = node.right!!
            }
            stack.addFirst(node)
            if (node.down != null) {
                node = node.down!!
            } else {
                break
            }
        } while (true)
        return stack
    }
}
