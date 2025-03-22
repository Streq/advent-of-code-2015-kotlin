package day08

import util.getFileAsString
import util.inputPath
import util.timeSolution
import java.util.logging.Logger

typealias InputType = String
typealias OutputType = Int

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    return contents
}

fun countCharacters(str: String): OutputType {
    var ret = str
    var count = 0
    while (ret.isNotEmpty()) {
        val (codeSize, actualSize) =
            if ("""\\x[0-9a-f]{2}""".toRegex().matchesAt(ret, 0))
                4 to 1
            else if (ret.startsWith('"'))
                1 to 0
            else if (ret.startsWith('\\'))
                2 to 1
            else 1 to 1
        ret = ret.substring(codeSize)
        count += codeSize - actualSize

    }
    return count
}

fun countCharactersExpanded(str: String): OutputType {
    var count = 2
    for (c in str) {
        if (c == '"' || c == '\\') {
            ++count
        }
    }
    return count
}


class Day08 {
    companion object {
        val LOG: Logger = Logger.getLogger(Day08::class.java.name)
    }

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): OutputType {
            return solve(inputFromFile(input))
        }

        fun solveFromRawFile(fileContents: String): OutputType {
            return solve(inputFromRawFile(fileContents))
        }

        fun solve(input: InputType): OutputType {
            println(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return input.lines().map { it.trim() }.filter { it.isNotBlank() }.sumOf { countCharacters(it) }
        }
    }

    object Part2 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): OutputType {
            return solve(inputFromFile(input))
        }

        fun solveFromRawFile(fileContents: String): OutputType {
            return solve(inputFromRawFile(fileContents))
        }

        fun solve(input: InputType): OutputType {
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return input.lines().map { it.trim() }.filter { it.isNotBlank() }.sumOf { countCharactersExpanded(it) }
        }
    }
}