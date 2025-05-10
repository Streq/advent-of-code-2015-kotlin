package day19

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day19Test {
    @Test
    fun testPart1() {
        assertEquals(4, Day19.Part1.solveFromRawFile("""
            H => HO
            H => OH
            O => HH
            
            HOH
        """.trimIndent()))
    }

    @Test
    fun testPart2() {
        assertEquals(6, Day19.Part2.solveFromRawFile("""
            e => H
            e => O
            H => HO
            H => OH
            O => HH

            HOHOHO
        """.trimIndent()))
    }


    @Test
    fun testPart2_2() {
        assertEquals(6, Day19.Part2.solveFromRawFile("""
            e => H
            e => O
            H => HO
            H => OH
            O => HH

            HOHOHO
        """.trimIndent()))
    }
}

