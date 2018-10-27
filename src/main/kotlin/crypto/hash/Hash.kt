package crypto.hash

import crypto.hash.Hash.ZERO
import java.security.MessageDigest

object Hash {
    val ZERO get() = ByteArray(1)

    private val digest = MessageDigest.getInstance("SHA-256")

    fun hash(arg1: ByteArray, arg2: ByteArray): ByteArray {
        return if (arg1 < arg2) {
            digest.digest(arg1 + arg2)
        } else {
            digest.digest(arg2 + arg1)
        }
    }

    private operator fun ByteArray.compareTo(other: ByteArray): Int {
        val cmp = size.compareTo(other.size)
        if (cmp != 0)
            return cmp
        for (i in 0 until size) {
            if (this[i] != other[i]) {
                return this[i].compareTo(other[i])
            }
        }
        return 0
    }
}

fun Any?.toBytes(): ByteArray {
    if (this == null) {
        return ZERO
    }
    return this.toString().toByteArray()
}
