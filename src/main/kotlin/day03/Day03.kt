package day03

import util.getFileAsString
import util.inputPath
import util.timeSolution
import java.util.logging.Logger

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): String {
    return getFileAsString(path)
}

data class Point(val x: Int, val y: Int)
data class MutPoint(var x: Int, var y: Int)
fun MutPoint.toPoint(): Point = Point(x, y)


class Day03 {
    companion object {
        val LOG: Logger = Logger.getLogger(Day03::class.java.name)
    }

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solveFromFile(Input.PATH))
        }

        fun solveFromFile(input: String): Long {
            return solve(inputFromFile(input))
        }

        fun solve(input: String): Long {
            println(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: String): Long {
            val set: MutableSet<Point> = mutableSetOf()
            var p = Point(0, 0)
            set.add(p)
            for (c in input) {
                when (c) {
                    '>' -> p = p.copy(x = p.x + 1)
                    'v' -> p = p.copy(y = p.y + 1)
                    '<' -> p = p.copy(x = p.x - 1)
                    '^' -> p = p.copy(y = p.y - 1)
                    else -> {}
                }
                set.add(p)
            }

            return set.size.toLong()
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

        fun solve(input: String): Long {
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: String): Long {
            val set: MutableSet<Point> = mutableSetOf()
            val p0 = MutPoint(0, 0)
            val p1 = MutPoint(0, 0)
            set.add(p0.toPoint())
            var p = p1
            for (c in input) {
                p = if (p === p1) p0 else p1
                when (c) {
                    '>' -> p.x += 1
                    'v' -> p.y += 1
                    '<' -> p.x -= 1
                    '^' -> p.y -= 1
                    else -> {}
                }
                set.add(p.toPoint())
            }

            return set.size.toLong()
        }
    }
}