package day10

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day10Test {


    @Test
    fun testRegexWorks() {
        assertEquals(7, REGEX.findAll("1321131112").count())
        assertEquals(listOf("1", "3", "2", "11", "3", "111", "2"), REGEX.findAll("1321131112").map { it.value }.toList())
    }

    @Test
    fun testBadConway() {
        assertEquals(4, Num("11133", 1).expand())
        assertEquals("312331233123".length.toLong(), Num("111331113311133", 1).expand())
        assertEquals("13111223111223111213".length.toLong(), Num("312331233123", 1).expand())

    }

    @Test
    fun testPart1() {
        assertEquals(1, Num("1", 0).expand())
        assertEquals(2, Num("1", 1).expand())
        assertEquals(2, Num("1", 2).expand())
        assertEquals(4, Num("1", 3).expand())
        assertEquals(6, Num("1", 4).expand())
        assertEquals(6, Num("1", 5).expand())
    }
}

