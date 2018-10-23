package crypto

interface MirrovAuthenticatedDictionary<T> : AuthenticatedDictionary<T> {

    fun initialize(elements: Collection<T>)

    fun insert(o: T)

    fun remove(o: T)
}