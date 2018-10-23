package crypto.skiplist

interface CryptoSet<T: Comparable<T>> {
    fun size(): Int

    fun isEmpty(): Boolean = size() == 0

    fun find(element: T): CryptoPath

    fun insert(element: T): Boolean

    fun delete(element: T)

    fun structureHash(): ByteArray

    fun insertAll(elements: Collection<T>) {
        elements.forEach { insert(it) }
    }
}