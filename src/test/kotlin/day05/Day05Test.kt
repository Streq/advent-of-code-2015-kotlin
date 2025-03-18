package day05

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day05Test {
    @Test
    fun testPart1() {
        assertEquals(1, Day05.Part1.solve("ugknbfddgicrmopn"))
        assertEquals(1, Day05.Part1.solve("aaa"))
        assertEquals(0, Day05.Part1.solve("jchzalrnumimnmhp"))
        assertEquals(0, Day05.Part1.solve("haegwjzuvuyypxyu"))
        assertEquals(0, Day05.Part1.solve("dvszwmarrgswjxmb"))
    }

    @Test
    fun testPart2() {
        assertEquals(1, Day05.Part2.solve("qjhvhtzxzqqjkmpb"))
        assertEquals(1, Day05.Part2.solve("xxyxx"))
        assertEquals(0, Day05.Part2.solve("uurcxstgmygtbstg"))
        assertEquals(0, Day05.Part2.solve("ieodomkazucvgmuy"))
    }
}

