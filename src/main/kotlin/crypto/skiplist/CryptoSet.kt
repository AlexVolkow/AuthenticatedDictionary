package crypto.skiplist

interface CryptoSet<T: Comparable<T>>: Iterable<T> {
    fun size(): Int

    fun isEmpty(): Boolean = size() == 0

    fun find(element: T): CryptoPath

    fun insert(element: T): Boolean

    fun insert(element: T, height: Int): Boolean

    fun remove(element: T): Boolean

    fun structureHash(): ByteArray

    fun insertAll(elements: Collection<T>) {
        elements.forEach { insert(it) }
    }

    fun contains(element: T): Boolean {
        return find(element).isFound
    }
}