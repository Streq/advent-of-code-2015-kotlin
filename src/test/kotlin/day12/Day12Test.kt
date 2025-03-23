package day12

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day12Test {
    @Test
    fun testPart1() {
        assertEquals(6, Day12.Part1.solveFromRawFile("""[1,2,3]"""))
        assertEquals(6, Day12.Part1.solveFromRawFile("""{"a":2,"b":4}"""))
        assertEquals(3, Day12.Part1.solveFromRawFile("""[[[3]]]"""))
        assertEquals(3, Day12.Part1.solveFromRawFile("""{"a":{"b":4},"c":-1}"""))
        assertEquals(0, Day12.Part1.solveFromRawFile("""{"a":[-1,1]}"""))
        assertEquals(0, Day12.Part1.solveFromRawFile("""[-1,{"a":1}]"""))
        assertEquals(0, Day12.Part1.solveFromRawFile("""[]"""))
        assertEquals(0, Day12.Part1.solveFromRawFile("""{}"""))
    }

    @Test
    fun testPart2() {
        assertTrue(""""\w+":"red"""".toRegex().matchesAt("""{"hola":"red",[]}""", 1))

        assertEquals(6, Day12.Part2.solveFromRawFile("""[1,2,3]"""))
        assertEquals(4, Day12.Part2.solveFromRawFile("""[1,{"c":"red","b":2},3]"""))
        assertEquals(0, Day12.Part2.solveFromRawFile("""{"d":"red","e":[1,2,3,4],"f":5}"""))
        assertEquals(6, Day12.Part2.solveFromRawFile("""[1,"red",5]"""))
        assertEquals(11, Day12.Part2.solveFromRawFile("""{"a":"sa","b":{"c":32,"s":"red"},"d":2,"e":[1,"red",5,{"a":3},{"a":"red",{"d":1}}]"""))
    }
}

