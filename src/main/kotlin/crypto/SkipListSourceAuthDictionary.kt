package crypto

class SkipListSourceAuthDictionary<T : Comparable<T>> : SkipListAuthDictionary<T>(), SourceAuthenticatedDictionary<T> {

    override fun insert(o: T): Update<T> {
        return if (data.insert(o)) {
            authBasis = Basis(data.structureHash())
            InsertUpdate(o)
        } else {
            NoUpdate()
        }
    }

    fun insert(o: T, height: Int): Update<T> {
        return if (data.insert(o, height)) {
            authBasis = Basis(data.structureHash())
            InsertUpdate(o)
        } else {
            NoUpdate()
        }
    }

    override fun remove(o: T): Update<T> {
        return if (data.remove(o)) {
            authBasis = Basis(data.structureHash())
            RemoveUpdate(o)
        } else {
            NoUpdate()
        }
    }

    override fun toString() = data.toString()
}