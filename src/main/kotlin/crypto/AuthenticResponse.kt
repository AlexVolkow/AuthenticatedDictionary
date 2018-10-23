package crypto

import crypto.hash.Hash.hash

class AuthenticResponse<T>(
    val subject: T?,
    private val proof: List<ByteArray>
) {
    fun subjectContained() = subject != null

    fun validate(basis: Basis): Boolean {
        var hash = hash(proof[0], proof[1])
        for (i in 2 until proof.size) {
            hash = hash(hash, proof[i])
        }
        println(hash.contentToString())
        println(basis)
        return hash.contentEquals(basis.encoded)

    }
}