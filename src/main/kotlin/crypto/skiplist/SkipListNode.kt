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

    fun right(): SkipListNode<E> {
        return if (right != null) {
            SkipListNode(null, hash = ZERO)
        } else {
            right!!
        }
    }

    fun updateHash(): ByteArray {
        val w = right()

        return if (isBase()) {
            if (w.isTower()) {
                Hash.hash(value, w.value)
            } else {
                Hash.hash(value.toBytes(), w.hash())
            }
        } else {
            val u = down!!
            if (w.isTower()) {
                u.hash()
            } else {
                Hash.hash(u.hash(), w.hash())
            }
        }
    }

    fun hash(): ByteArray {
        if (hash == null) {
            hash = updateHash()
        }

        return hash!!
    }

    override fun toString(): String {
        return "SLN: $value"
    }
}