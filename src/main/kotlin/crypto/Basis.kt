package crypto

import java.util.*

class Basis(encoded: ByteArray) {
    private val storedEncoded = encoded.copyOf()
    val encoded get() = storedEncoded.copyOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Basis

        if (!Arrays.equals(storedEncoded, other.storedEncoded)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(storedEncoded)
    }

    override fun toString(): String {
        return Arrays.toString(encoded)
    }
}