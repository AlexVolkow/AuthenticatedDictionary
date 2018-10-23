package crypto

import crypto.skiplist.CryptoSet
import crypto.skiplist.SkipList

open class SkipListAuthDictionary<T : Comparable<T>> : AuthenticatedDictionary<T> {

    protected val data: CryptoSet<T> = SkipList()
    protected var authBasis = Basis(ByteArray(0))

    override fun size(): Int = data.size()

    override fun contains(o: T): AuthenticResponse<T> {
        val path = data.find(o)
        return if (path.isFound) {
            AuthenticResponse(o, path.path)
        } else {
            AuthenticResponse(null, path.path)
        }
    }

    override fun getBasis(): Basis = authBasis

    override fun validateBasis(basis: Basis): Boolean = basis == authBasis
}