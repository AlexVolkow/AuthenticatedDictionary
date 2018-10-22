package crypto.hash

import java.security.MessageDigest

object Hash {
    private val digest = MessageDigest.getInstance("SHA-256")

    fun hash(arg1: ByteArray, arg2: ByteArray): ByteArray {
        return digest.digest(if (arg1 < arg2) {
            arg1 + arg2
        } else {
            arg2 + arg1
        })
    }

    operator fun ByteArray.compareTo(other: ByteArray): Int {
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