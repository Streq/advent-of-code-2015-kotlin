package dayN

import util.getFileAsString
import util.inputPath
import util.timeSolution

typealias InputType = String

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): String {
    return getFileAsString(path)
}

class DayN {

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): Long {
            return solve(inputFromFile(input))
        }

        fun solve(input: InputType): Long {
            println(input)
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): Long {
            return 0
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

        fun solve(input: InputType): Long {
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): Long {
            return 0
        }
    }
}