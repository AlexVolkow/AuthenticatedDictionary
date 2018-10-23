package crypto.skiplist

class SkipList<E : Comparable<E>> : CryptoSet<E> {

    companion object {
        private const val PROBABILITY = 0.5
    }

    private val head: SkipListNode<E>
    private var maxLevel: Int = 0
    private var size: Int = 0

    init {
        size = 0
        maxLevel = 0
        head = SkipListNode(null)
        head.nextNodes.add(null)
    }

    override fun size(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(element: E): Boolean {
        if (contains(element)) return false
        size++
        var level = 0
        while (Math.random() < PROBABILITY)
            level++
        while (level > maxLevel) {
            head.nextNodes.add(null)
            maxLevel++
        }
        val newNode = SkipListNode(element)
        var current: SkipListNode<E> = head
        do {
            current = findNext(element, current, level)
            newNode.nextNodes.add(0, current.nextNodes[level])
            current.nextNodes[level] = newNode
        } while (level-- > 0)
        return true
    }

    override fun delete(element: E) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun structureHash(): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(element: E): CryptoPath {
        val node = find(element)
        return node.value != null && node.value.compareTo(element) == 0
    }

    fun contains(element: E): Boolean {
        return find(element).isFound
    }

    private fun find(e: E, current: SkipListNode<E> = head, level: Int = maxLevel): SkipListNode<E> {
        var current = current
        var level = level
        do {
            current = findNext(e, current, level)
        } while (level-- > 0)
        return current
    }

    private fun findNext(e: E, current: SkipListNode<E>, level: Int): SkipListNode<E> {
        var current = current
        var next: SkipListNode<E>? = current.nextNodes[level]
        while (next != null) {
            val value = next.value
            if (e.compareTo(value) < 0)
                break

            current = next
            next = current.nextNodes[level]
        }
        return current
    }
}