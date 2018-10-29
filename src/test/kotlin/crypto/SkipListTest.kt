package crypto

import crypto.skiplist.CryptoSet
import crypto.skiplist.SkipList
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SkipListTest {

    @Test
    fun `test SkipList's functionality`() {
        val skipList = SkipList<Int>()
        randomTestBase(skipList, 1000)
    }

    companion object {
        fun randomTestBase(cryptoSet: CryptoSet<Int>, size: Int) {
            val wereAdded = mutableSetOf<Int>()
            val notYetAdded = (0..size*10).asSequence().toMutableSet()
            val trace = mutableListOf<String>()
            val printTrace = { trace.joinToString(separator = "\n") }
            for (k in 0..size) {
                val i = (0..5).shuffled().first()
                when (i) {
                    0 -> {
                        //add new value
                        val x = notYetAdded.shuffled().first()
                        cryptoSet.insert(x)
                        wereAdded.add(x)
                        notYetAdded.remove(x)
                        trace.add("insert $x")
                    }
                    1 -> {
                        //add value that was in the list
                        if (wereAdded.isEmpty()) {
                            assertEquals(0, cryptoSet.size(), "Accidentally not empty.\n ${printTrace()}")
                        } else {
                            val x = wereAdded.shuffled().first()
                            cryptoSet.insert(x)
                            trace.add("insert $x")
                        }
                    }
                    2 -> {
                        //delete value that wasn't in the list
                        val x = notYetAdded.shuffled().first()
                        cryptoSet.remove(x)
                        trace.add("delete $x")
                    }
                    3 -> {
                        //delete value that was in the list
                        if (wereAdded.isEmpty()) {
                            assertEquals(0, cryptoSet.size(), "Accidentally not empty.\n ${printTrace()}")
                        } else {
                            val x = wereAdded.shuffled().first()
                            cryptoSet.remove(x)
                            wereAdded.remove(x)
                            notYetAdded.add(x)
                            trace.add("delete $x")
                        }
                    }
                    4 -> {
                        //find added value
                        if (wereAdded.isEmpty()) {
                            assertEquals(0, cryptoSet.size(), "Accidentally not empty.\n ${printTrace()}")
                        } else {
                            val element = wereAdded.shuffled().first()
                            assertTrue(cryptoSet.contains(element), "Not found $element.\n ${printTrace()} \n$cryptoSet")
                        }
                    }
                    5 -> {
                        //find not added value
                        val element = notYetAdded.shuffled().first()
                        assertFalse(cryptoSet.contains(element), "Found $element.\n ${printTrace()}")
                    }
                }
            }
        }
    }
}