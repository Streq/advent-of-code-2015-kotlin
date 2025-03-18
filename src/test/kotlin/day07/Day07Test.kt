package day07

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

const val example1 = """
123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
"""
val expected1 = """
d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
""".trim().lines().sorted().joinToString("\n")

class Day07Test {
    @Test
    fun testPart1() {
        assertEquals(expected1, Day07.Part1.solveFromRawFile(example1))
    }
}

