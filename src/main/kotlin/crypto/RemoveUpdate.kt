package crypto

class RemoveUpdate<T>(private val element : T) : Update<T> {

    override fun execute(dict: MirrovAuthenticatedDictionary<T>) {
        dict.remove(element)
    }
}