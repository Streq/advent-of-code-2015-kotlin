package day06

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test


class Day06Test {
    @Test
    fun testPart1() {
        assertEquals(1000_000, Day06.Part1.solve("turn on 0,0 through 999,999"))
        assertEquals(1000, Day06.Part1.solve("toggle 0,0 through 0,999"))
        assertEquals(
            1000_000 - 4, Day06.Part1.solve("turn on 0,0 through 999,999\nturn off 499,499 through 500,500")
        )
    }

    @Test
    fun testPart2() {
        assertEquals(1, Day06.Part2.solve("turn on 0,0 through 0,0"))
        assertEquals(2000000, Day06.Part2.solve("toggle 0,0 through 999,999"))
    }
}

