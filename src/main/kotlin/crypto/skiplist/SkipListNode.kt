package crypto.skiplist

import crypto.hash.Hash
import crypto.hash.Hash.ZERO
import crypto.hash.toBytes

class SkipListNode<E>(
    val value: E?,
    var right: SkipListNode<E>? = null,
    var down: SkipListNode<E>? = null,
    var up: SkipListNode<E>? = null,
    private var hash: ByteArray? = null
) {
    fun isTower() = up != null

    fun isPlateau() = up == null

    fun isBase() = down == null

    fun updateHash(): ByteArray {
        if(right == null) {
            hash = ZERO
            return hash!!
        }
        val w = right!!
        hash = if (isBase()) {
            if (w.isTower()) {
                Hash.hash(value.toBytes(), w.value.toBytes())
            } else {
                Hash.hash(value.toBytes(), w.hash())
            }
        } else {
            val u = down!!
            if (w.isTower()) {
                u.hash()
            } else {
                Hash.hash(w.hash(), u.hash())
            }
        }
        return hash!!
    }

    fun hash(): ByteArray {
        if (hash == null) {
            hash = updateHash()
        }
        return hash!!
    }

    override fun toString(): String {
        return when {
            value == null && right == null -> "+inf"
            value == null -> "-inf"
            else -> "SLN: $value"
        }
    }
}