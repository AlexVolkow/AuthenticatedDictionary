package crypto

import crypto.hash.Hash.hash

class AuthenticResponse<T>(
    val subject: T?,
    private val proof: List<ByteArray>
) {
    fun subjectContained() = subject != null

    fun validate(basis: Basis): Boolean {
        val hash = proof.fold(ByteArray(0)) { acc, x ->
            if (acc.isNotEmpty()) hash(acc, x) else x
        }
        println(hash.contentToString())
        println(basis)
        return hash.contentEquals(basis.encoded)
    }
}