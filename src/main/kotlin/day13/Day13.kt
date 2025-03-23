package day13

import util.*
import java.util.Comparator.comparing
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day13::class, Level.SEVERE)

typealias InputType = List<Relationship>
typealias OutputType = Long

data class Relationship(val a: String, val b: String, val happiness: Int)

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

val REGEX = """(\w+) would (gain|lose) (\d+) .+?(\w+)\.""".toRegex()
private fun inputFromRawFile(contents: String): InputType {
    return contents
        .lineSequence()
        .mapNotNull { REGEX.matchEntire(it)?.destructured }
        .map { (a, s, h, b) -> Relationship(a, b, (if (s == "lose") -1 else 1) * h.toInt()) }.toList()
}

class Day13 {

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
            LOG.fine { input.joinToString("\n") }
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return findBestHappinessPerf(happinessMap(input))
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
            val map = happinessMap(input)
            val you = "YOU"
            map[you] = mutableMapOf()
            for (key in map.keys) {
                map[key]!![you] = 0
                map[you]!![key] = 0
            }
            return findBestHappinessPerf(map)
        }
    }
}

@Suppress("unused")
fun findBestHappinessBruteforce(map: Map<String, Map<String, Int>>): Long {
    val paths: MutableList<List<String>> = mutableListOf()
    val people: Set<String> = map.keys

    val firstPerson = people.first()

    fun getPaths(path: List<String>): List<List<String>> {
        val remainingPeople = people - path.toSet()
        if (remainingPeople.isEmpty()) {
            return listOf(path)
        }
        return remainingPeople.flatMap { getPaths(path + it) }
    }

    paths += getPaths(listOf(firstPerson))

    fun happiness(a: String, b: String): Long {
        val to = map[a]!![b]!!
        val from = map[b]!![a]!!
        val ret = (to + from).toLong()
        LOG.fine { "$a-$b ($to) + $b-$a ($from) = $ret" }
        return ret
    }

    fun getHappiness(path: List<String>): Long {
        val first = path.first()
        val last = path.last()
        val sequentialHappiness = path.windowed(2) { it[0] to it[1] }.sumOf { (a, b) -> happiness(a, b) }
        val wrappedHappiness = happiness(first, last)
        val ret = sequentialHappiness + wrappedHappiness
        LOG.fine { "total: $ret" }
        return ret
    }

    val res = paths.map { it to getHappiness(it) }
        .sortedWith(comparing { it: Pair<List<String>, Long> -> it.second }
            .thenBy { it.first.joinToString() }).distinctBy { it.second }

    LOG.fine(res.joinToString("\n"))
    return res.last().second
}

fun findBestHappinessPerf(map: Map<String, Map<String, Int>>): Long {
    val people: Set<String> = map.keys

    fun happiness(a: String, b: String): Long {
        val to = map[a]!![b]!!
        val from = map[b]!![a]!!
        val ret = (to + from).toLong()
        LOG.fine { "$a-$b ($to) + $b-$a ($from) = $ret" }
        return ret
    }

    val cache: MutableMap<List<String>, Long> = mutableMapOf()
    fun getHappiness(path: List<String>): Long {
        if (path in cache) {
            return cache[path]!!
        }

        val first = path.first()
        val last = path.last()
        val sequentialHappiness = path.windowed(2) { it[0] to it[1] }.sumOf { (a, b) -> happiness(a, b) }
        val wrappedHappiness = happiness(first, last)
        val ret = sequentialHappiness + wrappedHappiness
        LOG.fine { "total: $ret" }

        cache[path] = ret
        return ret
    }

    fun getBestPath(path: List<String>): List<String> {
        val remainingPeople = people - path.toSet()
        if (remainingPeople.isEmpty()) {
            return path
        }
        return remainingPeople.map { getBestPath(path + it) }.maxBy { getHappiness(it) }
    }

    val path: List<String> = getBestPath(listOf(people.first()))


    val res = path to getHappiness(path)
    LOG.info(res)
    return res.second
}

private fun happinessMap(input: InputType): MutableMap<String, MutableMap<String, Int>> {
    return input.groupBy { it.a }
        .mapValues { e ->
            e.value
                .groupBy({ it.b }, { it.happiness })
                .mapValues { it.value.first() }.toMutableMap()
        }.toMutableMap()
}