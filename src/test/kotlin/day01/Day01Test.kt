package day01

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day01Test {
    @Test
    fun testPart1() {
        assertEquals(0, Day01.Part1.solve("(())"))
        assertEquals(0, Day01.Part1.solve("()()"))
        assertEquals(3, Day01.Part1.solve("((("))
        assertEquals(3, Day01.Part1.solve("(()(()("))
        assertEquals(3, Day01.Part1.solve("))((((("))
        assertEquals(-1, Day01.Part1.solve("())"))
        assertEquals(-1, Day01.Part1.solve("))("))
        assertEquals(-3, Day01.Part1.solve(")))"))
        assertEquals(-3, Day01.Part1.solve(")())())"))
    }

    @Test
    fun testPart2() {
        assertEquals(1, Day01.Part2.solve(")"))
        assertEquals(5, Day01.Part2.solve("()())"))
    }
}
