package day18

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day18Test {
    @Test
    fun testPart1() {
        assertEquals(
            4, Day18.Part1.solveFromRawFile(
                """
                    .#.#.#
                    ...##.
                    #....#
                    ..#...
                    #.#..#
                    ####..
                """.trimIndent(),
                4
            )
        )
    }

    @Test
    fun testPart2() {
        assertEquals(
            17, Day18.Part2.solveFromRawFile(
                """
                    .#.#.#
                    ...##.
                    #....#
                    ..#...
                    #.#..#
                    ####..
                """.trimIndent(),
                5
            )
        )
    }
}

