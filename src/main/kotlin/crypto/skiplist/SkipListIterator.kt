package crypto.skiplist

class SkipListIterator<E : Comparable<E>>(list: SkipList<E>) : Iterator<E> {
    private var current: SkipListNode<E>

    init {
        var head = list.getHead()
        while (head.down != null)
            head = head.down!!
        current = head
    }

    override fun hasNext(): Boolean {
        return current.right != null
    }

    override fun next(): E {
        current = current.right!!
        return current.value!!
    }
}