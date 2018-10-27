package crypto

import crypto.hash.Hash
import crypto.hash.Hash.hash
import java.util.*

class AuthenticResponse<T>(
    val subject: T?,
    private val proof: List<ByteArray>
) {
    fun subjectContained() = subject != null

    fun validate(basis: Basis): Boolean {
        val hash = proof.foldRight(ByteArray(0)) { x, acc ->
            if (acc.isNotEmpty()) hash(x, acc) else x
        }
        return hash.contentEquals(basis.encoded)
    }
}