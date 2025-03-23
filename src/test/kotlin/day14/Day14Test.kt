package day14

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day14Test {
    @Test
    fun testPart1() {
        assertEquals(
            1120, getWinnersAndDistanceAt(
                1000,
                inputFromRawFile(
                    """
                        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                    """.trimIndent()
                )
            ).second
        )
    }

    @Test
    fun testPart2() {
        assertEquals(
            689, getWinnersByNewScoreSystem(
                1000,
                inputFromRawFile(
                    """
                        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                    """.trimIndent()
                )
            ).second
        )
    }
}

