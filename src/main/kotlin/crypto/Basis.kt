package crypto

import java.util.*

data class Basis(val encoded: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Basis

        if (!Arrays.equals(encoded, other.encoded)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(encoded)
    }


}