package day11

import util.*
import java.util.logging.Level
import java.util.logging.Logger

typealias InputType = String
typealias OutputType = String

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    return contents
}

val LOG: Logger = Logger.getLogger(Day11::class.java.name).also { it.level = Level.INFO }

class Day11 {

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
            return getNextValidPerf(input)
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
            return getNextValidPerf(getNextValidPerf(input))
        }
    }
}


fun hasStraight3(str: CharSequence): Boolean {
    return str.windowed(3).any { abc -> abc.windowed(2).all { ab -> ab[1] == ab[0] + 1 } }
}

fun getOrNextWithStraight3(ret: StringBuilder) {
    if (hasStraight3(ret)) {
        return
    }

    val last = ret.length - 1
    var c3 = ret[last - 3]
    var c2 = ret[last - 2]
    var c1 = ret[last - 1]
    var c0 = ret[last]

    if (c1 == 'z') {
        ret.increment(last - 1)
        if (hasStraight3(ret)) {
            return
        }
        c3 = ret[last - 3]
        c2 = ret[last - 2]
        c1 = ret[last - 1]
        c0 = ret[last]
    }

    if (c2 == 'z') {
        ret.increment(last - 2)
        if (hasStraight3(ret)) {
            return
        }
        c3 = ret[last - 3]
        c2 = ret[last - 2]
        c1 = ret[last - 1]
        c0 = ret[last]
    }

    if (c1 < c2 + 1) {
        c1 = c2 + 1
        ret[last - 1] = c1
        c0 = 'a'
        ret[last] = c0
        if (c2 == c3 + 1) {
            return
        }

        if (c1 == 'z') {
            ret.increment(last - 1)
            if (hasStraight3(ret)) {
                return
            }
            c3 = ret[last - 3]
            c2 = ret[last - 2]
            c1 = ret[last - 1]
            c0 = ret[last]
        }

    }

    if (c1 == c2 + 1 && c0 < c1 + 1 && c1 + 1 != 'z') {
        if (c1 + 1 > 'z')
            LOG.info("what")
        ret[last] = c1 + 1
        return
    }

    ret.increment()
    return


}

fun hasNoBanned(str: CharSequence): Boolean {
    return """[^iol]+""".toRegex().matches(str)
}

fun getOrNextWithoutBanned(ret: StringBuilder) {
    val match = """[iol]""".toRegex().find(ret) ?: return
    ret.increment(match.range.first)
}


fun has2Repeats(str: CharSequence): Boolean {
    return """(.)\1""".toRegex().findAll(str, 0).count() >= 2
}

fun getOrNextWith2Repeats(str: StringBuilder) {
    val matches = """(.)\1""".toRegex().findAll(str, 0).toList()
    if (matches.size >= 2) {
        return
    }

    if (matches.isEmpty() || matches.first().range.last >= str.length - 2) {
        // find first match

        val d = str[str.length - 4]
        val c = str[str.length - 3]

        if (d > c) {
            str[str.length - 3] = d
        } else {
            str[str.length - 4] = d + 1
            str[str.length - 3] = if (str[str.length - 4] != str[str.length - 5]) {
                d + 1
            } else 'a'
        }
        str[str.length - 2] = 'a'
        str[str.length - 1] = 'a'

        return
    }

    val a = str[str.length - 1]
    val b = str[str.length - 2]
    if (a > b) {
        str[str.length - 1] = b + 1
        str[str.length - 2] = b + 1
    } else {
        str[str.length - 1] = b
    }

}

fun isValid(str: CharSequence): Boolean {
    return hasStraight3(str) && hasNoBanned(str) && has2Repeats(str)
}


@Suppress("unused")
fun getNextValid(input: InputType): String {
    var ret = increment(input)
    var i = 0
    while (!isValid(ret)) {
        ret = increment(ret)
        i += 1
    }
    LOG.info("made $i checks")
    return ret
}


fun getNextValidPerf(input: InputType): String {
    val ret = StringBuilder(input)
    ret.increment()
    var i = 0
    while (!isValid(ret)) {
        getOrNextWithStraight3(ret)
        getOrNextWithoutBanned(ret)
        getOrNextWith2Repeats(ret)
        i += 1
    }
    LOG.info("made $i checks")
    return ret.toString()
}

fun increment(str: String): String {
    val ret = StringBuilder(str)
    ret.increment()
    return ret.toString()
}

fun StringBuilder.increment(indexToIncrement: Int = 7) {
    for ((index, c) in withIndex().reversed()) {
        if (c == 'z' || index > indexToIncrement) {
            this[index] = 'a'
            continue
        }
        this[index] = c + 1
        break
    }
}
