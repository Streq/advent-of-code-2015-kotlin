@file:Suppress("NAME_SHADOWING")

package day17

import util.*
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day17::class, Level.ALL)

typealias InputType = List<Int>
typealias OutputType = Int

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

val REGEX = """(\d+)""".toRegex()
private fun inputFromRawFile(contents: String): InputType {
    return contents.lineSequence().mapNotNull { REGEX.matchEntire(it)?.destructured }.map { (a) -> a.toInt() }.toList()
}


class Day17 {

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
            return storageCombinations(150, input)
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
            return minStorageCombinations(150, input)!!.count
        }
    }
}

fun storageCombinations(litersToStore: Int, emptyContainers: List<Int>, fullContainers: List<Int> = listOf()): Int {
    var count = 0

    for ((i, container) in emptyContainers.withIndex()) {
        val litersMinusContainer = litersToStore - container
        if (litersMinusContainer == 0) {
            LOG.fine { (fullContainers + container).toString() }
            count += 1
        } else if (litersMinusContainer > 0) {
            count += storageCombinations(litersMinusContainer, emptyContainers.drop(i + 1), fullContainers + container)
        }
    }

    return count
}


data class StorageSolution(val count: Int, val minContainers: Int)

fun minStorageCombinations(
    litersToStore: Int,
    emptyContainers: List<Int>,
    fullContainers: List<Int> = listOf(),
    minContainers: Int = Int.MAX_VALUE
): StorageSolution? {
    var count = 0
    var minContainers = minContainers

    for ((i, container) in emptyContainers.withIndex()) {
        val newLitersToStore = litersToStore - container
        val newFullContainers = fullContainers + container
        val containerCount = newFullContainers.size
        if (newLitersToStore == 0) {
            if (containerCount < minContainers) {
                minContainers = containerCount
                count = 0
            }
            LOG.fine(newFullContainers)
            count += 1
        } else if (newLitersToStore > 0 && containerCount < minContainers) {
            val solution = minStorageCombinations(
                litersToStore = newLitersToStore,
                emptyContainers = emptyContainers.drop(i + 1),
                fullContainers = newFullContainers,
                minContainers = minContainers
            ) ?: continue
            val newMinContainers = solution.minContainers
            if (newMinContainers > minContainers) {
                continue
            }
            if (newMinContainers < minContainers) {
                minContainers = newMinContainers
                count = 0
            }
            count += solution.count

        }
    }

    if (count == 0) {
        return null
    }
    return StorageSolution(count = count, minContainers = minContainers)
}
