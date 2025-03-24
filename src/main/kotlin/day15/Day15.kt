package day15

import util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.max

val LOG: Logger = logger(Day15::class, Level.FINE)

typealias InputType = List<Ingredient>
typealias OutputType = Int

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

val REGEX = """(\w+).*?(-?\d+).*?(-?\d+).*?(-?\d+).*?(-?\d+).*?(-?\d+)""".toRegex()
private fun inputFromRawFile(contents: String): InputType {
    return contents.lineSequence().mapNotNull { REGEX.matchEntire(it)?.destructured }
        .map { (n, a, b, c, d, e) -> Ingredient(n, Contents(a.toInt(), b.toInt(), c.toInt(), d.toInt(), e.toInt())) }
        .toList()
}


class Day15 {

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
            return getHighest(Contents(0, 0, 0, 0, 0), 100, input)
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
            return getHighest500(Contents(0, 0, 0, 0, 0), 100, input)
        }
    }
}

data class Ingredient(
    val name: String,
    val contents: Contents
)

data class Contents(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
) {
    operator fun plus(e: Contents): Contents {
        return Contents(
            capacity + e.capacity,
            durability + e.durability,
            flavor + e.flavor,
            texture + e.texture,
            calories + e.calories
        )
    }

    operator fun times(b: Int): Contents {
        return Contents(capacity * b, durability * b, flavor * b, texture * b, calories * b)
    }

    fun points(): Int {
        return max(capacity, 0) * max(durability, 0) * max(flavor, 0) * max(texture, 0)
    }

    fun pointsIf500(): Int {
        return if (calories != 500) 0 else max(capacity, 0) * max(durability, 0) * max(flavor, 0) * max(texture, 0)
    }
}

private fun getHighest(
    mixture: Contents,
    remainingSpoons: Int,
    ingredients: List<Ingredient>
): OutputType {
    val current = ingredients.first()
    val remaining = ingredients.drop(1)
    val stats = current.contents
    if (remaining.isEmpty()) {
        return (mixture + (stats * remainingSpoons)).points()
    }

    var highest = 0
    for (spoonCount in 0..remainingSpoons) {
        highest = max(highest, getHighest(mixture + stats * spoonCount, remainingSpoons - spoonCount, remaining))
    }
    return highest
}


private fun getHighest500(
    mixture: Contents,
    remainingSpoons: Int,
    ingredients: List<Ingredient>
): OutputType {
    val current = ingredients.first()
    val remaining = ingredients.drop(1)
    val stats = current.contents
    if (remaining.isEmpty()) {
        return (mixture + (stats * remainingSpoons)).pointsIf500()
    }

    var highest = 0
    for (spoonCount in 0..remainingSpoons) {
        val newMixture = mixture + stats * spoonCount
        highest = max(highest, getHighest500(newMixture, remainingSpoons - spoonCount, remaining))
    }
    return highest
}
