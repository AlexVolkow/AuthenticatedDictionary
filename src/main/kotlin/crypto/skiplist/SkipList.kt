package crypto.skiplist

import crypto.hash.toBytes
import java.util.*
import kotlin.collections.ArrayList

class SkipList<E : Comparable<E>> : CryptoSet<E> {

    private val random = Random()
    private var root: SkipListNode<E>
    private var size: Int = 0

    init {
        size = 0
        root = SkipListNode(null)
        root.hash()
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
        val stack = findPath(element)

        if (stack.peekFirst().value == element) {
            return false
        }

        size++

        var towerNode: SkipListNode<E>? = null
        do {
            val top = if (stack.isNotEmpty()) {
                stack.pollFirst()
            } else {
                val newRoot = SkipListNode(null, null, root)
                root.up = newRoot
                root = newRoot
                newRoot
            }

            val newNode = SkipListNode(element, top.right, towerNode)
            top.right = newNode
            towerNode?.up = newNode
            towerNode = newNode

            newNode.updateHash()
            top.updateHash()

            var prevNode = top
            while (stack.isNotEmpty() && stack.peekFirst().right == prevNode) {
                prevNode = stack.pollFirst()
                prevNode.updateHash()
            }
        } while (random.nextBoolean())

        while (stack.isNotEmpty()) {
            stack.pollFirst().updateHash()
        }

        return true
    }

    override fun structureHash(): ByteArray {
        return root.hash()
    }

    override fun find(element: E): CryptoPath {
        val path = findPath(element)
        val m = path.size

        val v1 = path.pollFirst()

        val isFound = v1.value == element

        val proof = ArrayList<ByteArray>()
        val w1 = v1.right()
        if (w1.isPlateau()) {
            proof.add(w1.hash())
        } else {
            proof.add(w1.value.toBytes())
        }

        proof.add(v1.toBytes())

        var j = 1
        var prevNode = v1
        for (i in 1 until m) {
            val v = path.pollFirst()
            val w = v.right()
            if (w.isPlateau()) {
                j++
                if (w != prevNode) {
                    proof.add(w.hash())
                } else {
                    if (v.isBase()) {

                    }
                }
            }
            prevNode = w
        }
    }

    override fun contains(element: E): Boolean {
        var node = root

        do {
            while (node.right != null && node.right!!.value!! <= element) {
                node = node.right!!
            }
            if (node.down != null) {
                node = node.down!!
            } else {
                break
            }
        } while (true)

        if (node.value == null) {
            return false
        }
        return node.value!!.compareTo(element) == 0
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
            while (node.right != null && node.right!!.value!! <= element) {
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