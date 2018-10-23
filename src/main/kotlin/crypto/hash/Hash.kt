package crypto.hash

import crypto.hash.Hash.ZERO
import java.security.MessageDigest

object Hash {
    val ZERO = ByteArray(1)
    val ONE = ByteArray(1) {1}
    val TWO = ByteArray(1) {2}

    private val digest = MessageDigest.getInstance("SHA-256")

    fun hash(arg1: ByteArray, arg2: ByteArray): ByteArray {
        return /*digest.digest*/(if (arg1 < arg2) {
            ONE + arg1 + TWO + arg2
        } else {
            ONE + arg2 + TWO + arg1
        })
    }

    fun <T> hash(arg1: T?, arg2: T?) :ByteArray {
        return hash(arg1.toBytes(), arg2.toBytes())
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

fun Any?.toBytes(): ByteArray {
    if (this == null) {
        return ZERO
    }
    return this.toString().toByteArray()
}