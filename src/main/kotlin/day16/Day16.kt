package day16

import util.*
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day16::class, Level.ALL)

typealias InputType = List<Aunt>
typealias OutputType = List<Aunt>

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

val NUM_REGEX = """Sue (\d+)""".toRegex()
val ITEM_REGEX = """(\w+): (\d+)""".toRegex()

val EVIDENCE = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1,
)

data class Aunt(val id: Int, val knownThings: Map<String, Int>) {
    fun matches(evidence: Map<String, Int>): Boolean {
        val keysToCheck = evidence.keys intersect knownThings.keys
        for (key in keysToCheck) {
            val e = evidence[key]!!
            val k = knownThings[key]!!
            if (e != k) {
                LOG.fine { "rejecting aunt $this because of $key, $e != $k" }
                return false
            }
        }
        LOG.fine { "aunt $this matches evidence" }
        return true
    }

    fun matchesPart2(evidence: Map<String, Int>): Boolean {
        val keysToCheck = evidence.keys intersect knownThings.keys
        for (key in keysToCheck) {
            val e = evidence[key]!!
            val k = knownThings[key]!!


            val (condition, strCond) = when (key) {
                "cats", "trees" -> (k > e) to ">"
                "pomeranians", "goldfish" -> (k < e) to "<"
                else -> (k == e) to "=="
            }

            if (!condition) {
                LOG.fine { "rejecting aunt $this because of $key, !($e $strCond $k)" }
                return false
            }
        }
        LOG.fine { "aunt $this matches evidence" }
        return true
    }
}

private fun inputFromRawFile(contents: String): InputType {
    val ret = contents.lineSequence().filter { it.startsWith("Sue") }
        .map {
            Aunt(
                NUM_REGEX.find(it)!!.groupValues[1].toInt(),
                ITEM_REGEX.findAll(it).map { it.destructured }.map { (a, b) -> a to b.toInt() }.toMap()
            )
        }.toList()
    return ret
}


class Day16 {

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
            LOG.fine(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return input.filter { it.matches(EVIDENCE) }
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
            LOG.fine(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return input.filter { it.matchesPart2(EVIDENCE) }
        }
    }
}

