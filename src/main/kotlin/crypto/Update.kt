package crypto

interface Update<T> {
    fun execute(dict: SourceAuthenticatedDictionary<T>)
}