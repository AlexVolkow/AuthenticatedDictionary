package crypto

import crypto.skiplist.ListSet
import crypto.skiplist.SkipList
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SkipListTest {

    @Test
    fun simpleTest() {
        val skiplist = SkipList<Int>()

        skiplist.insert(69)
        skiplist.insert(451)
        skiplist.insert(1984)

        assertTrue(skiplist.find(69))
        assertTrue(skiplist.find(451))
        assertTrue(skiplist.find(1984))
    }

    private class Adapter(): ListSet<Int> {
        private val x = mutableSetOf<Int>()

        override fun size(): Int { return x.size        }

        override fun isEmpty() = x.isEmpty()

        override fun find(element: Int): Boolean {
            return element in x
        }

        override fun insert(element: Int) {
            x.add(element)
        }

        override fun delete(element: Int) {
            x.remove(element)
        }
    }

    @Test
    fun `test SkipList's functionality`() {
        val skipList = SkipList<Int>()
//        val skipList = Adapter()
        val wereAdded = mutableSetOf<Int>()
        val notYetAdded = (0..1000).asSequence().toMutableSet()
        val trace = mutableListOf<String>()
        val printTrace = { trace.joinToString(separator = "\n") }
        for (k in 0..1000) {
            val i = (0..5).shuffled().first()
            when (i) {
                0 -> {
                    //add new value
                    val x = notYetAdded.shuffled().first()
                    skipList.insert(x)
                    wereAdded.add(x)
                    notYetAdded.remove(x)
                    trace.add("insert $x")
                }
                1 -> {
                    //add value that was in the list
                    if (wereAdded.isEmpty()) {
                        assertEquals(0, skipList.size(), "Accidentally not empty.\n ${printTrace()}")
                    } else {
                        val x = wereAdded.shuffled().first()
                        skipList.insert(x)
                        trace.add("insert $x")
                    }
                }
                2 -> {
                    //delete value that wasn't in the list
                    val x = notYetAdded.shuffled().first()
                    skipList.delete(x)
                    trace.add("delete $x")
                }
                3 -> {
                    //delete value that was in the list
                    if (wereAdded.isEmpty()) {
                        assertEquals(0, skipList.size(), "Accidentally not empty.\n ${printTrace()}")
                    } else {
                        val x = wereAdded.shuffled().first()
                        skipList.delete(x)
                        wereAdded.remove(x)
                        notYetAdded.add(x)
                        trace.add("delete $x")
                    }
                }
                4 -> {
                    //find added value
                    if (wereAdded.isEmpty()) {
                        assertEquals(0, skipList.size(), "Accidentally not empty.\n ${printTrace()}")
                    } else {
                        val element = wereAdded.shuffled().first()
                        assertTrue(skipList.find(element), "Not found $element.\n ${printTrace()}")
                    }
                }
                5 -> {
                    //find not added value
                    val element = notYetAdded.shuffled().first()
                    assertFalse(skipList.find(element), "Found $element.\n ${printTrace()}")
                }
            }
        }
    }
}