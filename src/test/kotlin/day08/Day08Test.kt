package day08

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day08Test {
    @Test
    fun testPart1() {
        assertEquals(2 - 0, Day08.Part1.solveFromRawFile(""""""""))
        assertEquals(5 - 3, Day08.Part1.solveFromRawFile(""""abc""""))
        assertEquals(10 - 7, Day08.Part1.solveFromRawFile(""""aaa\"aaa""""))
        assertEquals(6 - 1, Day08.Part1.solveFromRawFile(""""\x27""""))

        assertEquals(
            12, Day08.Part1.solveFromRawFile(
                """
                ""
                "abc"
                "aaa\"aaa"
                "\x27"
                """.trimIndent()
            )
        )
    }

    @Test
    fun testPart2() {
        assertEquals(6 - 2, Day08.Part2.solveFromRawFile(""""""""))
        assertEquals(9 - 5, Day08.Part2.solveFromRawFile(""""abc""""))
        assertEquals(16 - 10, Day08.Part2.solveFromRawFile(""""aaa\"aaa""""))
        assertEquals(11 - 6, Day08.Part2.solveFromRawFile(""""\x27""""))

        assertEquals(
            19, Day08.Part2.solveFromRawFile(
                """
                ""
                "abc"
                "aaa\"aaa"
                "\x27"
                """.trimIndent()
            )
        )
    }
}

