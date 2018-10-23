package crypto

class InsertUpdate<T>(private val element : T) : Update<T>  {

    override fun execute(dict: MirrovAuthenticatedDictionary<T>) {
        dict.insert(element)
    }
}