package crypto.skiplist

interface CryptoSet<T: Comparable<T>> {
    fun size(): Int

    fun isEmpty(): Boolean = size() == 0

    fun find(element: T): CryptoPath

    fun insert(element: T): Boolean

    fun insert(element: T, height: Int): Boolean

    fun remove(element: T): Boolean

    fun structureHash(): ByteArray

    fun contains(element: T): Boolean {
        return find(element).isFound
    }
}