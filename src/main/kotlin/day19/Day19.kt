package day19

import util.*
import java.util.Comparator.comparing
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day19::class, Level.FINEST)

typealias InputType = InputData
typealias OutputType = Int

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}


data class Replacement(val atom: Regex, val expansion: Regex)

data class InputData(val replacements: List<Replacement>, val initialMolecule: String)

private fun inputFromRawFile(contents: String): InputType {
    val split = contents.split("\n\n", limit = 2)
    val replacements =
        split[0].lineSequence().map { it -> it.split(" => ", limit = 2) }
            .map { it -> Replacement(it[0].toRegex(), it[1].toRegex()) }
            .toList()
    val input = split[1].trim()

    return InputData(replacements, input)
}


class Day19 {

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
            val ret = applyReplacements(input.replacements, input.initialMolecule)

            return ret.size
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
            val finalMolecule = input.initialMolecule
            val inverseReplacements = input.replacements.map { Replacement(it.expansion, it.atom) }
                // shortest first so that longest transformations get added last -> processed first
                .sortedBy { it.atom.pattern.length }.toList()

            val beforeToAfterMap = mutableMapOf<String, String>()
            val stack = ArrayDeque<String>()
            stack.addFirst(finalMolecule)

            var shortestMoleculeYet = finalMolecule
            while (stack.isNotEmpty()) {
                val after = stack.removeLast()
                val count = countTraversal(beforeToAfterMap, finalMolecule, after)
                val befores = applyReplacements(inverseReplacements, after)
                for (before in befores) {
                    if (before.contains("e")) {
                        if (before.length > 1) {
                            LOG.fine("trimmed $before for having e")
                            continue
                        } else {
                            val ret = count + 1
                            LOG.severe("found an e at step $ret")
                            return ret
                        }
                    }
                    if (before !in beforeToAfterMap) {
                        beforeToAfterMap += before to after
                        stack += before
                        val oldShortest = shortestMoleculeYet
                        shortestMoleculeYet = minOf(shortestMoleculeYet, before, comparing { it.length })
                        if (oldShortest != shortestMoleculeYet) {
                            LOG.severe { "new shortest: $shortestMoleculeYet" }
                        }
                        continue
                    }
                    LOG.finer("trimmed already processed key $before")

                    val preexistingAfter = beforeToAfterMap[before]!!
                    val preexistingCount = countTraversal(beforeToAfterMap, finalMolecule, preexistingAfter)
                    if (preexistingCount > count) {
                        beforeToAfterMap += before to after
                    }
                }
            }
            LOG.info(beforeToAfterMap)
            return countTraversal(beforeToAfterMap, finalMolecule, "e")
        }
    }
}

private fun countTraversal(map: Map<String, String>, root: String, leaf: String): Int {
    var i = 0
    var current = leaf
    while (current != root) {
        current = map[current]!!
        i++
    }
    return i
}

private fun applyReplacements(replacements: List<Replacement>, string: String): List<String> {
    val set = mutableListOf<String>()
    val str = string
    LOG.fine(str)
    var i = 0
    for (replacement in replacements) {
        val regex = replacement.atom
        val expansion = replacement.expansion
        val v = regex.findAll(str).toList()
        if (v.isEmpty()) {
            continue
        }
        i += 1
        LOG.finer { "${regex.pattern} => $expansion" }
        v.withIndex().map { (i, it) ->

            val range = it.range
            val ret = str.replaceRange(range, expansion.pattern)

            LOG.finest {
                "$i - ${
                    str.replaceRange(
                        range,
                        "[" + str.substring(range) + "]"
                    )
                } => $ret"
            }

            ret

        }.distinct()
            .forEach { it -> set.add(it) }
    }
    return set
}