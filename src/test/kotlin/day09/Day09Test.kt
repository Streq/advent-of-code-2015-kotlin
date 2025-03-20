package day09

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day09Test {
    @Test
    fun testBothParts() {
        assertEquals(
            """
            Belfast -> London -> Dublin = 982
            Dublin -> Belfast -> London = 659
            Belfast -> Dublin -> London = 605
            """.trimIndent(),
            Day09.solveFromRawFile(
                """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141
                """.trimIndent()
            )
        )
    }
}

