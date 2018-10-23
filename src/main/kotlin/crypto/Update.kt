package crypto

interface Update<T> {
    fun execute(dict: MirrowAuthenticatedDictionary<T>)

}
