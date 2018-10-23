package crypto

class SkipListMirrovAuthDictionary<T : Comparable<T>> : SkipListAuthDictionary<T>() , MirrovAuthenticatedDictionary<T> {

    override fun initialize(elements: Collection<T>) {
        data.insertAll(elements)
    }

    override fun insert(o: T) {
        data.insert(o)
    }

    override fun remove(o: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}