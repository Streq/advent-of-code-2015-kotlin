package day06

import util.getFileAsString
import util.inputPath
import util.timeSolution
import kotlin.math.max

typealias InputType = String

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): String {
    return getFileAsString(path)
}

private val REGEX = "(\\w+ ?\\w+) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex()

class Day06 {

    class Grid(width: UInt, height: UInt) {
        val width = width.toInt()
        val height = height.toInt()
        val array = BooleanArray(this.width * this.height) { false }

        fun setRect(left: Int, top: Int, right: Int, bot: Int, value: Boolean) {
            for (j in top..bot) {
                for (i in left..right) {
                    array[j * width + i] = value
                }
            }
        }

        fun toggleRect(left: Int, top: Int, right: Int, bot: Int) {
            for (j in top..bot) {
                for (i in left..right) {
                    array[j * width + i] = !array[j * width + i]
                }
            }
        }
    }

    class GridInt(width: UInt, height: UInt) {
        val width = width.toInt()
        val height = height.toInt()
        val array = IntArray(this.width * this.height) { 0 }

        fun addRect(left: Int, top: Int, right: Int, bot: Int, value: Int) {
            for (j in top..bot) {
                for (i in left..right) {
                    val newValue = get(i, j) + value
                    array[j * width + i] = max(0, newValue)
                }
            }
        }

        fun get(x: Int, y: Int) = array[y * width + x]
    }

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
            val grid = Grid(1000U, 1000U)
            input.lines().map { REGEX.matchEntire(it) }.mapNotNull { it?.groups }.map { it.mapNotNull { it?.value } }
                .forEach {
                    val action = it[1]
                    val left = it[2].toInt()
                    val top = it[3].toInt()
                    val right = it[4].toInt()
                    val bot = it[5].toInt()
                    when (action) {
                        "turn on" -> grid.setRect(left, top, right, bot, true)
                        "turn off" -> grid.setRect(left, top, right, bot, false)
                        "toggle" -> grid.toggleRect(left, top, right, bot)
                    }

                }


            return grid.array.count { it }.toLong()
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
            val grid = GridInt(1000U, 1000U)
            input.lines().map { REGEX.matchEntire(it) }.mapNotNull { it?.groups }.map { it.mapNotNull { it?.value } }
                .forEach {
                    val increment = when (it[1]) {
                        "turn on" -> 1
                        "turn off" -> -1
                        "toggle" -> 2
                        else -> 0
                    }
                    val left = it[2].toInt()
                    val top = it[3].toInt()
                    val right = it[4].toInt()
                    val bot = it[5].toInt()
                    grid.addRect(left, top, right, bot, increment)
                }


            return grid.array.sum().toLong()
        }

    }
}