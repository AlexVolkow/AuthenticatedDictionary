package crypto

interface MirrowAuthenticatedDictionary<T> : AuthenticatedDictionary<T> {

    fun inisilize(elements: Collection<T>)

    fun insert(o: T)

    fun remove(o: T)
}