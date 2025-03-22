package day11

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day11Test {
    @Test
    fun testChecks() {
        assertTrue(hasStraight3("hijklmmn"))
        assertFalse(hasNoBanned("hijklmmn"))
        assertFalse(has2Repeats("hijklmmn"))

        assertFalse(hasStraight3("abbceffg"))
        assertTrue(hasNoBanned("abbceffg"))
        assertTrue(has2Repeats("abbceffg"))


        assertFalse(hasStraight3("abbcegjk"))
        assertTrue(hasNoBanned("abbcegjk"))
        assertFalse(has2Repeats("abbcegjk"))
    }

    @Test
    fun testIncrement() {
        assertEquals("b", increment("a"))
        assertEquals("a", increment("z"))
        assertEquals("ba", increment("az"))
        assertEquals("baa", increment("azz"))
    }

    @Test
    fun testPart1() {
        assertEquals("abcdffaa", Day11.Part1.solveFromRawFile("abcdefgh"))
        assertEquals("ghjaabcc", Day11.Part1.solveFromRawFile("ghijklmn"))
    }
}

