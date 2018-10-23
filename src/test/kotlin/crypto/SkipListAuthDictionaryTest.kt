package crypto

import crypto.skiplist.SkipList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SkipListAuthDictionaryTest {

    private val trustedSource = SkipListSourceAuthDictionary<String>()
    private val mirror = SkipListMirrovAuthDictionary<String>()

    init {
        trustedSource.insert("Hello").execute(mirror)
        trustedSource.insert("World ").execute(mirror)
    }

    @Test
    fun `test object found`() {
        val basis = trustedSource.getBasis()
        val query = mirror.contains("Hello")
        assertTrue(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test object not found`() {
        val basis = trustedSource.getBasis()
        val query = mirror.contains("Arina")
        assertFalse(query.subjectContained())
        assertTrue(query.validate(basis))
    }

    @Test
    fun `test basis changed by source`() {
        val basis = trustedSource.getBasis()
        trustedSource.insert("of Tanks").execute(mirror)
        val query = mirror.contains("Hello")
        assertTrue(query.subjectContained())
        assertFalse(query.validate(basis))
    }

    @Test
    fun `test basis changed by user`() {
        val skipList = SkipList<String>()
        skipList.insert("ABC")
        assertFalse(mirror.contains("Hello").validate(Basis(skipList.structureHash())))
    }
}