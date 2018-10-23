package crypto

interface AuthenticatedDictionary<T> {
    fun size(): Int

    fun isEmpty(): Boolean = size() == 0

    fun contains(o: T): AuthenticResponse<T>

    fun getBasis(): Basis

    fun validateBasis(basis: Basis): Boolean
}