package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SkipListTest {

    @Test
    fun simpleTest() {
        val skiplist = SkipList<Int>()

      //  assertFalse(skiplist.find(111))

        skiplist.insert(69)
        skiplist.insert(451)
        skiplist.insert(1984)

        assertTrue(skiplist.find(69))
        assertTrue(skiplist.find(451))
        assertTrue(skiplist.find(1984))
    }
}