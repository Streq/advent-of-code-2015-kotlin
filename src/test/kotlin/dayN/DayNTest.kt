package dayN

import org.junit.jupiter.api.Assertions.*
import util.exampleInputPath
import kotlin.test.Test

object Input {
    val EXAMPLE: String = exampleInputPath(object {}.javaClass)
}

class DayNTest {
    @Test
    fun testPart1() {
        assertEquals(0, DayN.Part1.solveFromFile(Input.EXAMPLE));
    }

    @Test
    fun testPart2() {
        assertEquals(0, DayN.Part2.solveFromFile(Input.EXAMPLE));
    }
}

