package crypto

class InsertUpdate<T>(private val element : T) : Update<T>  {

    override fun execute(dict: MirrowAuthenticatedDictionary<T>) {
        dict.insert(element)
    }

}