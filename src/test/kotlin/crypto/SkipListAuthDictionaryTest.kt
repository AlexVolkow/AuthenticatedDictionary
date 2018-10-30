package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SkipListAuthDictionaryTest {

    fun createSource() = SkipListSourceAuthDictionary<Int>().apply {
        insert(12)
        insert(17)
        insert(20)
        insert(22)
        insert(25)
        insert(31)
        insert(38)
        insert(39)
        insert(44)
        insert(50)
        insert(55)
    }


    @Test
    fun `test object found`() {
        val trustedSource = createSource()
        println(trustedSource)
        val query = trustedSource.contains(39)
        val basis = trustedSource.getBasis()

        assertTrue(query.subjectContained())
        assertEquals(query.subject, 39)
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test object not found`() {
        val trustedSource = createSource()
        val basis = trustedSource.getBasis()
        val query = trustedSource.contains(18)
        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test basis changed by source`() {
        val trustedSource = createSource()
        val basis = trustedSource.getBasis()
        trustedSource.insert(99)
        val query = trustedSource.contains(39)
        assertTrue(query.subjectContained())
        assertFalse(query.validate(basis))
    }

    @Test
    fun `test basis changed by user`() {
        val trustedSource = createSource()
        val skipList = SkipList<String>()
        skipList.insert("ABC")
        assertFalse(trustedSource.validateBasis(Basis(skipList.structureHash())))
    }

    @Test
    fun `test removing`() {
        val trustedSource = createSource()
        with(trustedSource) {
            remove(25)
            remove(39)
            remove(1)
        }
        val wereInserted = listOf(12, 17, 20, 22, 31, 38, 44, 50, 55)
        wereInserted.forEach {
            assertTrue(trustedSource.contains(it).subjectContained())
        }
        assertFalse(trustedSource.contains(25).subjectContained())
        assertFalse(trustedSource.contains(39).subjectContained())
        assertFalse(trustedSource.contains(1).subjectContained())
    }

    @Test
    fun `random source's functionality`() {
        val adapter = SourceAdapter<Int>()
        SkipListTest.randomTestBase(adapter, 1000)
    }
}