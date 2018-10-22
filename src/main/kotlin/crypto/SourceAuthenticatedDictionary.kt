package crypto

interface SourceAuthenticatedDictionary<T> : AuthenticatedDictionary<T> {
    fun insert(o: T): Update<T>

    fun remove(o: T): Update<T>
}