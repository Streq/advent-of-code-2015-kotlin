package day18


import util.*
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day18::class, Level.FINE)

typealias InputType = Grid<Char>
typealias OutputType = Int

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    val lines = contents.trim().lines()
    val ret = InputType(lines.first().length, lines.size) { _, _ -> '.' }
    for ((j, line) in lines.withIndex()) {
        for ((i, c) in line.withIndex()) {
            ret.set(i, j, c)
        }
    }
    return ret
}


class Day18 {

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH, 100))
        }

        fun solveFromFile(input: String, blinks: Int): OutputType {
            return solve(inputFromFile(input), blinks)
        }

        fun solveFromRawFile(fileContents: String, blinks: Int): OutputType {
            return solve(inputFromRawFile(fileContents), blinks)
        }

        fun solve(input: InputType, blinks: Int): OutputType {
            LOG.fine { "$input\n\n" }
            return LOG.timeSolution { solveInternal(input, blinks) }
        }

        private fun solveInternal(input: InputType, blinks: Int): OutputType {
            return solveBlinks(input, blinks) { a, b -> blink(a, b) }
        }

    }

    object Part2 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH, 100))
        }

        fun solveFromFile(input: String, blinks: Int): OutputType {
            return solve(inputFromFile(input), blinks)
        }

        fun solveFromRawFile(fileContents: String, blinks: Int): OutputType {
            return solve(inputFromRawFile(fileContents), blinks)
        }

        fun solve(input: InputType, blinks: Int): OutputType {
            LOG.fine { input.toString() }
            return LOG.timeSolution { solveInternal(input, blinks) }
        }

        private fun solveInternal(input: InputType, blinks: Int): OutputType {
            lightCorners(input)
            return solveBlinks(input, blinks) { a, b -> blinkPart2(a, b) }
        }
    }
}


private fun solveBlinks(input: InputType, blinks: Int, fn: (InputType, InputType) -> Unit): Int {
    var frame0 = input
    var frame1 = InputType(frame0.width, frame0.height) { x, y -> frame0.get(x, y) }
    LOG.fine { "$frame0\n" }
    for (i in 0 until blinks) {
        fn(frame0, frame1)
        frame0 = frame1.also { frame1 = frame0 }
        LOG.fine { "$frame0\n" }

    }

    return frame0.asSequence().count { it == '#' }
}

fun blink(frame0: Grid<Char>, frame1: Grid<Char>) {
    frame0.forEach { x, y, v ->
        val v1 = applyRules(frame0, x, y, v)
        frame1.set(x, y, v1)
    }
}

private fun applyRules(frame0: Grid<Char>, x: Int, y: Int, v: Char): Char {
    val neighborCount = countNeighborsOn(frame0, x, y)
    val v1 = when (v) {
        '#' -> when (neighborCount) {
            2, 3 -> '#'
            else -> '.'
        }

        '.' -> when (neighborCount) {
            3 -> '#'
            else -> '.'
        }

        else -> throw IllegalStateException()
    }
    return v1
}

fun blinkPart2(frame0: Grid<Char>, frame1: Grid<Char>) {
    blink(frame0, frame1)
    lightCorners(frame1)
}

private fun lightCorners(frame: Grid<Char>) {
    DIRS_DIAG_U.map { it.first * (frame.width - 1) to it.second * (frame.height - 1) }
        .forEach { frame.set(it.first, it.second, '#') }
}

fun countNeighborsOn(frame: Grid<Char>, x: Int, y: Int): Int =
    DIRS_ALL_LAYOUT_ORDER.count { frame.getOrElse(x + it.first, y + it.second, '.') == '#' }