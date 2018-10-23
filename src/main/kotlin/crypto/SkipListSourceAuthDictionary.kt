package crypto

import crypto.skiplist.CryptoSet
import crypto.skiplist.SkipList

class SkipListSourceAuthDictionary<T : Comparable<T>> : SourceAuthenticatedDictionary<T> {
    private val data: CryptoSet<T> = SkipList()
    private var basis: Basis = Basis(ByteArray(0))

    override fun insert(o: T): Update<T> {
        return if (data.insert(o)) {
            basis = Basis(data.structureHash())
            InsertUpdate(o)
        } else {
            NoUpdate<T>()
        }
    }

    override fun remove(o: T): Update<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Int = data.size()

    override fun contains(o: T): AuthenticResponse<T> {
        val path = data.find(o)
        return if (path.isFound) {
            AuthenticResponse(o, path.path)
        } else {
            AuthenticResponse(null, path.path)
        }
    }

    override fun getBasis(): Basis = basis

    override fun validateBasis(basis: Basis): Boolean = basis == this.basis
}