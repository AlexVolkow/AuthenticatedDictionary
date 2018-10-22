package crypto.skiplist

import java.util.*

class Node<T: Comparable<T>>(
    val data: T? = null,
    var right: Node<T>? = null,
    var down: Node<T>? = null,
    var hash: ByteArray? = null
) : Comparable<T> {
    override fun compareTo(other: T): Int {
        return data?.compareTo(other) ?: -1
    }

    override fun toString(): String {
        return "Node(data=$data, hash=${Arrays.toString(hash)})"
    }
}