package crypto

class NoUpdate<T> : Update<T> {
    override fun execute(dict: MirrowAuthenticatedDictionary<T>) {}
}