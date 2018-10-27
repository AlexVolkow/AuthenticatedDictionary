package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SkipListAuthDictionaryTest {

    private val trustedSource = SkipListSourceAuthDictionary<Int>()


    init {
        with(trustedSource) {
            insert(12, 2)
            insert(17, 5)
            insert(20, 1)
            insert(22, 1)
            insert(25, 4)
            insert(31, 3)
            insert(38, 2)
            insert(39, 1)
            insert(44, 2)
            insert(50, 1)
            insert(55, 4)
        }
        println(trustedSource)
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
    fun `test object not found`() {
        val basis = trustedSource.getBasis()
        val query = trustedSource.contains(18)
        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test basis changed by source`() {
        val basis = trustedSource.getBasis()
//        trustedSource.insert("of Tanks")
//        val query = trustedSource.contains("Hello, ")
//        assertTrue(query.subjectContained())
//        assertFalse(query.validate(basis))
    }

    @Test
    fun `test basis changed by user`() {
        val skipList = SkipList<String>()
//        skipList.insert("ABC")
//        assertFalse(trustedSource.validateBasis(Basis(skipList.structureHash())))
    }
}