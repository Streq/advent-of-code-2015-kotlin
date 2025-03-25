package day17

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day17Test {
    @Test
    fun testPart1() {
        assertEquals(4, storageCombinations(25, listOf(20, 15, 10, 5, 5)))
    }
    @Test
    fun testPart2() {
        assertEquals(3, minStorageCombinations(25, listOf(20, 15, 10, 5, 5))!!.count)
    }
}

