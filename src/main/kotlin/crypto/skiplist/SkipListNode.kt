package crypto.skiplist

import java.util.*

class SkipListNode<E>(val value: E?) {
    var nextNodes: MutableList<SkipListNode<E>?> = ArrayList()

    fun level(): Int {
        return nextNodes.size - 1
    }

    override fun toString(): String {
        return "SLN: $value"
    }
}