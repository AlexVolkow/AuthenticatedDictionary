package crypto.skiplist

interface ListSet<T: Comparable<T>> {
    fun size(): Int

    fun isEmpty(): Boolean = size() == 0

    fun find(element: T): Boolean

    fun insert(element: T)

    fun delete(element: T)

    fun insertAll(elements: Collection<T>) {
        elements.forEach { insert(it) }
    }
}