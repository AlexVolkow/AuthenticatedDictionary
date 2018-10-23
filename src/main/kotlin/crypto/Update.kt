package crypto

interface Update<T> {
    fun execute(dict: MirrovAuthenticatedDictionary<T>)

}
