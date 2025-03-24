package day15

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day15Test {
    @Test
    fun testPart1() {
        assertEquals(
            62842880, Day15.Part1.solveFromRawFile(
                """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                """.trimIndent()
            )
        )
    }

    @Test
    fun testPart2() {
        assertEquals(
            57600000, Day15.Part2.solveFromRawFile(
                """
                    Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                    Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                """.trimIndent()
            )
        )
    }
}

