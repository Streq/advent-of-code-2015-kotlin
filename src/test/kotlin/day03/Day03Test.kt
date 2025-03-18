package day03

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day03Test {
    @Test
    fun testPart1() {
        assertEquals(2, Day03.Part1.solve(">"))
        assertEquals(4, Day03.Part1.solve("^>v<"))
        assertEquals(2, Day03.Part1.solve("^v^v^v^v^v"))
    }

    @Test
    fun testPart2() {
        assertEquals(3, Day03.Part2.solve("^v"))
        assertEquals(3, Day03.Part2.solve("^>v<"))
        assertEquals(11, Day03.Part2.solve("^v^v^v^v^v"))
    }
}

