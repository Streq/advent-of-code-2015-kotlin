package day04

import org.junit.jupiter.api.Assertions.*
import util.exampleInputPath
import kotlin.test.Test

object Input {
    val EXAMPLE: String = exampleInputPath(object {}.javaClass)
}

class Day04Test {
    @Test
    fun testMD5() {
        assertTrue(hash( "abcdef609043").startsWith("000001dbbfa"))
        assertTrue(hash( "pqrstuv1048970").startsWith("000006136ef"))
    }


    @Test
    fun testPart1() {
        assertEquals(609043, Day04.Part1.solve("abcdef"))
        assertEquals(1048970, Day04.Part1.solve("pqrstuv"))
    }

    @Test
    fun testPart2() {
        assertEquals(0, Day04.Part2.solveFromFile(Input.EXAMPLE))
    }
}

