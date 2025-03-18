package day05

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

private val VOWEL_REGEX = "[aeiou]\\w*".repeat(3).toRegex()
private val REPEAT_REGEX = "(\\w)\\1".toRegex()
private val BLACKLIST_REGEXES = arrayOf("ab", "cd", "pq", "xy").joinToString("|").toRegex()
fun isNice(s: String): Boolean {
    return s.contains(VOWEL_REGEX) && s.contains(REPEAT_REGEX) && !s.contains(BLACKLIST_REGEXES)
}

private val REPEAT_REGEX2 = "(\\w).\\1".toRegex()
private val REPEAT_PAIR_REGEX = "(\\w{2}).*\\1".toRegex()
fun isNice2(s: String): Boolean {
    return s.contains(REPEAT_REGEX2) && s.contains(REPEAT_PAIR_REGEX)
}

class Day05 {

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
            return input.lines().count { isNice(it) }.toLong()
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
            return input.lines().count { isNice2(it) }.toLong()
        }
    }
}