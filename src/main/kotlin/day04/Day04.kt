package day04

import util.getFileAsString
import util.inputPath
import util.timeSolution
import java.security.MessageDigest
import java.util.logging.Logger

typealias InputType = String

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): String {
    return getFileAsString(path)
}

object MD5 {
    val instance = MessageDigest.getInstance("MD5")
}

fun hash(s: String): String {
    val inputBytes = s.toByteArray()
    val outputBytes = MD5.instance.digest(inputBytes)
    return outputBytes
        .joinToString("") { it.toUByte().toString(16).padStart(2, '0') }
}


class Day04 {
    companion object {
        val LOG: Logger = Logger.getLogger(Day04::class.java.name)
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
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): Long {
            var i = 0
            while (true) {
                if (hash("$input$i").startsWith("00000")) {
                    return i.toLong()
                }
                ++i
            }
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
            return LOG.timeSolution { solveInternal(input) }
        }


        private fun solveInternal(input: InputType): Long {
            var i = 0
            while (true) {
                if (hash("$input$i").startsWith("000000")) {
                    return i.toLong()
                }
                ++i
            }
        }
    }
}