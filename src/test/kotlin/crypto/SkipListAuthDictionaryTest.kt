package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SkipListAuthDictionaryTest {

    private val trustedSource = SkipListSourceAuthDictionary<Int>()

    init {
        with(trustedSource) {
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
    }

    @Test
    fun `test object found`() {
        println(trustedSource)
        val query = trustedSource.contains(39)
        val basis = trustedSource.getBasis()

        assertTrue(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `simple test`() {
        val source = SkipListSourceAuthDictionary<Int>()
        source.insert(12)

        val query = source.contains(12)
        val basis = source.getBasis()

        assertTrue(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `simple not found test`() {
        val source = SkipListSourceAuthDictionary<Int>()
        source.insert(12, 0)
        source.insert(44, 0)
        source.insert(52, 0)

        val query = source.contains(33)
        val basis = source.getBasis()

        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test object not found`() {
        val basis = trustedSource.getBasis()
        val query = trustedSource.contains(18)
        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test basis changed by source`() {
        val basis = trustedSource.getBasis()
        trustedSource.insert(99)
        val query = trustedSource.contains(39)
        assertTrue(query.subjectContained())
        assertFalse(query.validate(basis))
    }

    @Test
    fun `test basis changed by user`() {
        val skipList = SkipList<String>()
        skipList.insert("ABC")
        assertFalse(trustedSource.validateBasis(Basis(skipList.structureHash())))
    }
}