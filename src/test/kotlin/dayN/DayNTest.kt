package dayN

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class DayNTest {
    @Test
    fun testPart1() {
        assertEquals(0, DayN.Part1.solveFromRawFile(""""""))
    }

    @Test
    fun testPart2() {
        assertEquals(0, DayN.Part2.solveFromFile(""""""))
    }
}

