package crypto

class SkipListSourceAuthDictionary<T : Comparable<T>> : SkipListAuthDictionary<T>(), SourceAuthenticatedDictionary<T> {

    fun insert(o: T, height: Int): Update<T> {
        return if (data.insert(o, height)) {
            authBasis = Basis(data.structureHash())
            InsertUpdate(o)
        } else {
            NoUpdate()
        }
    }

    override fun insert(o: T): Update<T> {
        return if (data.insert(o)) {
            authBasis = Basis(data.structureHash())
            InsertUpdate(o)
        } else {
            NoUpdate()
        }
    }

    override fun remove(o: T): Update<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString() = data.toString()
}