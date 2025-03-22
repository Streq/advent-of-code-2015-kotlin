package day01

import util.getFileAsString
import util.inputPath
import util.timeSolution
import java.util.logging.Logger

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): String {
    return getFileAsString(path)
}

class Day01 {

    companion object {
        val LOG: Logger = Logger.getLogger(Day01::class.java.name)
    }
    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): Long {
            return solve(inputFromFile(input))
        }

        fun solve(input: String): Long {
            println(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: String): Long {
            return (input.count { it == '(' } - input.count { it == ')' }).toLong()
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

        fun solve(input: String): Long {
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: String): Long {
            var floor = 0
            for ((i, c) in input.withIndex()) {
                when (c) {
                    '(' -> floor += 1
                    ')' -> {
                        floor -= 1
                        if (floor == -1) {
                            return (i + 1).toLong()
                        }
                    }
                }
            }
            return -1
        }
    }
}