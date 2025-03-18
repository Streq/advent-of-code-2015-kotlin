package day02

import util.getFileAsString
import util.inputPath
import util.timeSolution

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

data class Box(val w: Int, val l: Int, val h: Int) {
    fun totalRibbon(): Int {
        return smallestPerimeter() + volume()
    }

    fun totalArea(): Int {
        return surfaceArea() + slack()
    }

    private fun surfaceArea(): Int {
        return (2 * l * w) + (2 * w * h) + (2 * h * l)
    }

    private fun slack(): Int {
        return minOf(l * w, w * h, h * l)
    }

    private fun smallestPerimeter(): Int {
        return minOf(l * 2 + w * 2, w * 2 + h * 2, h * 2 + l * 2)
    }

    private fun volume(): Int {
        return w * l * h
    }
}


private fun inputFromFile(path: String): List<Box> {
    return getFileAsString(path).lines()
        .filter { it.isNotBlank() }
        .map { it.split("x").map { it.toInt() } }
        .map { Box(it[0], it[1], it[2]) }
}

class Day02 {

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): Long {
            return solve(inputFromFile(input))
        }

        fun solve(input: List<Box>): Long {
            println(input)
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: List<Box>): Long {
            return input.sumOf { it.totalArea() }.toLong()
        }
    }

    object Part2 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): Long {
            return solve(inputFromFile(input))
        }

        fun solve(input: List<Box>): Long {
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: List<Box>): Long {
            return input.sumOf { it.totalRibbon() }.toLong()
        }
    }
}