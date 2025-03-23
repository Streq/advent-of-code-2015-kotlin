package day12

import util.*
import java.util.logging.Level
import java.util.logging.Logger

typealias InputType = String
typealias OutputType = Long

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    return contents
}

val LOG: Logger = logger(Day12::class, Level.ALL)

class Day12 {

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
            return """-?\d+""".toRegex().findAll(input).map { it.value.toLong() }.sum()
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
            return (if (input.startsWith("{")) {
                evalObj(input)
            } else evalArr(input))
        }

        private fun evalObj(input: CharSequence): Long {
            if (hasRedProp(input)) {
                LOG.fine("discarding $input")
                return 0
            }
            LOG.finer("evaluating $input")
            var count: Long = 0
            var i = 0 // skip the first char because we assume it's '{'
            while (++i != input.length) {
                val c = input[i]
                when (c) {
                    '{' -> {
                        val obj = getObjSubsequence(input.subSequence(i, input.length))
                        count += evalObj(obj)
                        i += obj.length - 1
                        continue
                    }

                    '[' -> {
                        val arr = getArrSubsequence(input.subSequence(i, input.length))
                        count += evalArr(arr)
                        i += arr.length - 1
                        continue
                    }

                    ',', ':' -> {
                        continue
                    }

                    '"' -> {
                        i += """"\w+"""".toRegex().find(input, i)!!.value.length - 1
                        continue
                    }

                }
                val num = """-?\d+""".toRegex().find(input, i)
                if (num == null || num.range.first != i) {
                    continue
                }
                i += num.value.length - 1
                count += num.value.toLong()
            }

            LOG.fine("$count -> $input")
            return count
        }

        private fun evalArr(input: CharSequence): Long {
            LOG.finer("evaluating $input")

            var count: Long = 0
            var i = 0 // skip the first char because we assume it's '['
            while (++i != input.length) {
                val c = input[i]
                when (c) {
                    '{' -> {
                        val obj = getObjSubsequence(input.subSequence(i, input.length))
                        count += evalObj(obj)
                        i += obj.length - 1
                        continue
                    }

                    '[' -> {
                        val arr = getArrSubsequence(input.subSequence(i, input.length))
                        count += evalArr(arr)
                        i += arr.length - 1
                        continue
                    }

                    ',', ':' -> {
                        continue
                    }

                    '"' -> {
                        i += """"\w+"""".toRegex().find(input, i)!!.value.length - 1
                        continue
                    }
                }
                val num = """-?\d+""".toRegex().find(input, i)
                if (num == null || num.range.first != i) {
                    continue
                }
                i += num.value.length - 1
                count += num.value.toLong()
            }

            LOG.fine("$count -> $input")
            return count
        }

        private fun hasRedProp(input: CharSequence): Boolean {
            var i = 0 // skip the first char because we assume it's '{'
            while (++i != input.length) {
                val c = input[i]
                when (c) {
                    '"' -> {
                        if (""""\w+":"red"""".toRegex().matchesAt(input, i)) {
                            return true
                        }
                    }

                    '{' -> {
                        val obj = getObjSubsequence(input.subSequence(i, input.length))
                        i += obj.length - 1
                        continue
                    }

                    '[' -> {
                        val arr = getArrSubsequence(input.subSequence(i, input.length))
                        i += arr.length - 1
                        continue
                    }

                    else -> {
                        continue
                    }

                }
            }
            return false
        }

        private fun getArrSubsequence(input: CharSequence): CharSequence {
            var braceDebt = 1
            var i = 0 // skip the first char because we assume it's '['
            while (++i != input.length) {
                val c = input[i]
                when (c) {
                    '[' -> braceDebt += 1
                    ']' -> {
                        braceDebt -= 1
                        if (braceDebt == 0) {
                            return input.subSequence(0, i + 1)
                        }
                    }
                }
            }

            throw IllegalStateException()
        }

        private fun getObjSubsequence(input: CharSequence): CharSequence {
            var braceDebt = 1
            var i = 0 // skip the first char because we assume it's '{'
            while (++i != input.length) {
                val c = input[i]
                when (c) {
                    '{' -> braceDebt += 1
                    '}' -> {
                        braceDebt -= 1
                        if (braceDebt == 0) {
                            return input.subSequence(0, i + 1)
                        }
                    }
                }
            }
            throw IllegalStateException()
        }


    }
}