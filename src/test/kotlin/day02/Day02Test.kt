package day02

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day02Test {
    @Test
    fun testPart1() {
        assertEquals(58, Day02.Part1.solve(listOf(Box(2, 3, 4))))
        assertEquals(43, Day02.Part1.solve(listOf(Box(1, 1, 10))))
    }

    @Test
    fun testPart2() {
        assertEquals(34, Day02.Part2.solve(listOf(Box(2, 3, 4))))
        assertEquals(14, Day02.Part2.solve(listOf(Box(1, 1, 10))))
    }
}

