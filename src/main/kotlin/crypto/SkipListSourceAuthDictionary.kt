package crypto

import crypto.skiplist.ListSet
import crypto.skiplist.SkipList

class SkipListSourceAuthDictionary<T: Comparable<T>> :SourceAuthenticatedDictionary<T> {
    private val data: ListSet<T> = SkipList()

    override fun insert(o: T): Update<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(o: T): Update<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(o: T): AuthenticResponse<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBasis(): Basis {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validateBasis(basis: Basis): Basis {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}