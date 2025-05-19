package day20

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class Day20Test {


    @ParameterizedTest(name = "house N{0} is lowest house to have at least {1} presents")
    @MethodSource("testPart1Data")
    fun testPart1(expected: Long, input: Long) {
        assertEquals(expected, Day20.Part1.solve(input))
    }

    @ParameterizedTest(name = "house N{0} has {1} presents")
    @MethodSource("testPart1GetHousePresentsData")
    fun testPart1GetPresents(input: Long, expected: Long) {
        assertEquals(expected, Day20.Part1.getPresents(input))
    }

    @Test
    fun testPart2() {
        assertEquals(0, Day20.Part2.solveFromRawFile(""""""))
    }

    companion object {
        @JvmStatic
        fun testPart1Data(): List<Arguments> {
            return listOf(
                arguments(2, 30),
                arguments(3, 40),
                arguments(4, 70),
                arguments(4, 60),
                arguments(6, 120),
                arguments(6, 80),
                arguments(8, 150),
                arguments(8, 130),
                arguments(24, 300),
                arguments(84, 2000),
                arguments(50400, 2000000)
            )
        }

        @JvmStatic
        fun testPart1GetHousePresentsData(): List<Arguments> {
            return listOf(
                arguments(1, 1),
                arguments(2, 2 + 1),
                arguments(3, 3 + 1),
                arguments(4, 4 + 2 + 1),
                arguments(5, 5 + 1),
                arguments(6, 6 + 3 + 2 + 1),
                arguments(7, 7 + 1),
                arguments(8, 8 + 4 + 2 + 1),
                arguments(9, 9 + 3 + 1),
                arguments(10, 10 + 5 + 2 + 1),
                arguments(11, 11 + 1),
                arguments(12, 12 + 6 + 4 + 3 + 2 + 1),
                arguments(13, 13 + 1),
                arguments(14, 14 + 7 + 2 + 1),
                arguments(15, 15 + 5 + 3 + 1),
                arguments(16, 16 + 8 + 4 + 2 + 1),
                arguments(17, 17 + 1),
                arguments(18, 18 + 9 + 6 + 3 + 2 + 1),
                arguments(19, 19 + 1),
                arguments(20, 20 + 10 + 5 + 4 + 2 + 1),
                arguments(30, 30 + 15 + 10 + 6 + 5 + 3 + 2 + 1)
            ).map { it.get() }.map { arguments(it[0], (it[1]) as Int * 10) }
        }
    }
}

