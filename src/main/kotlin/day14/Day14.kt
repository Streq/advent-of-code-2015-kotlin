package day14

import util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.min

val LOG: Logger = logger(Day14::class, Level.SEVERE)

data class Reindeer(val name: String, val speed: Int, val runTime: Int, val restTime: Int)

fun Reindeer.getTraversedAfter(secs: Int): OutputType {
    val fullCycles = secs / (runTime + restTime)
    val fullCycleRunTime = fullCycles * runTime
    val incompleteCycleTime = secs % (runTime + restTime)
    val incompleteCycleRunTime = min(incompleteCycleTime, runTime)
    val totalRunTime = fullCycleRunTime + incompleteCycleRunTime
    val distance = totalRunTime * speed.toLong()
    LOG.finest {
        """
            
        $this 
        fullCycles: $fullCycles
        fullCycleRunTime: $fullCycleRunTime
        incompleteCycleTime: $incompleteCycleTime
        incompleteCycleRunTime: $incompleteCycleRunTime
        totalRunTime: $totalRunTime
        distance: $distance
        
        """.trimIndent()
    }
    LOG.finer { "$this -> $distance" }
    return distance
}


typealias InputType = List<Reindeer>
typealias OutputType = Long

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private val REGEX = """(\w+).*?(\d+).*?(\d+).*?(\d+).*""".toRegex()
fun inputFromRawFile(contents: String): InputType {
    return contents
        .lineSequence()
        .mapNotNull { REGEX.matchEntire(it)?.destructured }
        .map { (a, b, c, d) -> Reindeer(a, b.toInt(), c.toInt(), d.toInt()) }
        .toList()
}


class Day14 {

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
            return getWinnersAndDistanceAt(2503, input).second!!
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
            return getWinnersByNewScoreSystem(2503, input).second!!
        }
    }

}

fun getWinnersAndDistanceAt(secs: Int, input: InputType): Pair<List<Reindeer>, OutputType?> {
    return input.filterMax { it.getTraversedAfter(secs) }
}

fun getWinnersByNewScoreSystem(secs: Int, input: InputType): Pair<Set<Reindeer>, OutputType?> {
    if (input.isEmpty()) {
        return Pair(setOf(), null)
    }

    val map = input.associateWith { 0L }.toMutableMap()
    val digits = secs.toString().length
    LOG.fine { "${0.toString().padStart(digits)}: race begins" }
    for (sec in 0 until secs) {
        val nextSec = sec + 1
        val res = getWinnersAndDistanceAt(nextSec, input)
        val winners = res.first
        val max = res.second
        for (winner in winners) {
            map.compute(winner) { _, v -> v!! + 1 }
        }
        LOG.fine {
            "${
                nextSec.toString().padStart(digits)
            }: ${winners.joinToString { "${it.name}(${map[it]!!})" }} at the lead with ${max}km"
        }
    }

    val ret = map.filterMax { it.value }
    return Pair(ret.first.keys, ret.second)

}