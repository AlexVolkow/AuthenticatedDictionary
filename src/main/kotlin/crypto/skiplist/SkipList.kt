package crypto.skiplist

import java.util.*

class SkipList<T : Comparable<T>>: ListSet<T> {

    private val random = Random()
    private var root: Node<T> = Node()
    private var size: Int = 0

    override fun find(element: T): Boolean {
        var node = root

        do {
            while (node.right != null && node.right!! <= element) {
                node = node.right!!
            }
            if (node.down != null) {
                node = node.down!!
            } else {
                break
            }
        } while (true)

        return node.compareTo(element) == 0
    }

    override fun insert(element: T) {
        size++

        val stack: Deque<Node<T>> = findPath(element)

        var tmp: Node<T>? = null
        do {
            val top = if (stack.isEmpty()) {
                val newRoot = Node(null, null, root)
                root = newRoot
                newRoot
            } else {
                stack.pollFirst()
            }
            val newNode = Node(element, top.right, tmp)
            top.right = newNode
            tmp = newNode
        } while (random.nextBoolean())
    }

    override fun delete(element: T) {
        val stack: Deque<Node<T>> = findPath(element)

        var top = stack.peekFirst()
        while (stack.isNotEmpty() && top.right == element) {
            top.right = top.right?.right
            top = stack.pollFirst()
        }

        while (root.right == null && root.down != null) {
            root = root.down!!
        }
    }

    override fun size(): Int = size

    private fun findPath(element: T): Deque<Node<T>> {
        val stack: Deque<Node<T>> = LinkedList<Node<T>>()
        var node = root

        do {
            while (node.right != null && node < element) {
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