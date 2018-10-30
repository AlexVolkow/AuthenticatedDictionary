package crypto

import crypto.skiplist.CryptoPath
import crypto.skiplist.CryptoSet

class SourceAdapter<T : Comparable<T>> : CryptoSet<T> {
    private val dict = SkipListSourceAuthDictionary<T>()

    override fun size(): Int {
        return dict.size()
    }

    override fun find(element: T): CryptoPath {
        return CryptoPath(dict.contains(element).subjectContained(), dict.contains(element).proof)
    }

    override fun insert(element: T): Boolean {
        return dict.insert(element) !is NoUpdate
    }

    override fun insert(element: T, height: Int): Boolean {
        return dict.insert(element, height) !is NoUpdate
    }

    override fun remove(element: T): Boolean {
        return dict.remove(element) !is NoUpdate
    }

    override fun structureHash(): ByteArray {
        return dict.getBasis().encoded
    }
}