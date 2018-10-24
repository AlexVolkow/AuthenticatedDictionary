package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SkipListAuthDictionaryTest {

    private val trustedSource = SkipListSourceAuthDictionary<String>()


    init {
        println(trustedSource.validateBasis(trustedSource.getBasis()))
        trustedSource.insert("Hello, ")
        println(trustedSource.contains("Hello, ").validate(trustedSource.getBasis()))
        trustedSource.insert("World ")
        trustedSource.insert("A")
        trustedSource.insert("B")
        trustedSource.insert("C")
        println(trustedSource.contains("World ").validate(trustedSource.getBasis()))
    }

    @Test
    fun `test object found`() {
        println(trustedSource)
        val query = trustedSource.contains("Hello, ")
        val basis = trustedSource.getBasis()
        assertTrue(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test object not found`() {
        val basis = trustedSource.getBasis()
        val query = trustedSource.contains("Jello, ")
        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test basis changed by source`() {
        val basis = trustedSource.getBasis()
        trustedSource.insert("of Tanks")
        val query = trustedSource.contains("Hello, ")
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