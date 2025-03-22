package day10

import util.getFileAsString
import util.inputPath
import util.timeSolution
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

val REGEX = """(\d)\1*""".toRegex()

data class Num(val sequence: String, val height: Int)


fun Num.expand(): Long {
    return conwaySolve(sequence, height)
}

class Day10 {
    companion object {
        val LOG = Logger.getLogger(Day10::class.java.name)
    }
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
            println(input)
            return LOG.timeSolution { solveInternal(input) }
        }


        private fun solveInternal(input: InputType): OutputType {
            val num = Num(input, 40)
            return num.expand()
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
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            val num = Num(input, 50)
            return num.expand()
        }
    }
}

data class Element(val sequence: String, val decaysTo: List<String>)

val CONWAY_MAP: Map<String, Element> = mapOf(
    "H" to Element("22", listOf("H")),
    "He" to Element("13112221133211322112211213322112", listOf("Hf", "Pa", "H", "Ca", "Li")),
    "Li" to Element("312211322212221121123222112", listOf("He")),
    "Be" to Element("111312211312113221133211322112211213322112", listOf("Ge", "Ca", "Li")),
    "B" to Element("1321132122211322212221121123222112", listOf("Be")),
    "C" to Element("3113112211322112211213322112", listOf("B")),
    "N" to Element("111312212221121123222112", listOf("C")),
    "O" to Element("132112211213322112", listOf("N")),
    "F" to Element("31121123222112", listOf("O")),
    "Ne" to Element("111213322112", listOf("F")),
    "Na" to Element("123222112", listOf("Ne")),
    "Mg" to Element("3113322112", listOf("Pm", "Na")),
    "Al" to Element("1113222112", listOf("Mg")),
    "Si" to Element("1322112", listOf("Al")),
    "P" to Element("311311222112", listOf("Ho", "Si")),
    "S" to Element("1113122112", listOf("P")),
    "Cl" to Element("132112", listOf("S")),
    "Ar" to Element("3112", listOf("Cl")),
    "K" to Element("1112", listOf("Ar")),
    "Ca" to Element("12", listOf("K")),
    "Sc" to Element("3113112221133112", listOf("Ho", "Pa", "H", "Ca", "Co")),
    "Ti" to Element("11131221131112", listOf("Sc")),
    "V" to Element("13211312", listOf("Ti")),
    "Cr" to Element("31132", listOf("V")),
    "Mn" to Element("111311222112", listOf("Cr", "Si")),
    "Fe" to Element("13122112", listOf("Mn")),
    "Co" to Element("32112", listOf("Fe")),
    "Ni" to Element("11133112", listOf("Zn", "Co")),
    "Cu" to Element("131112", listOf("Ni")),
    "Zn" to Element("312", listOf("Cu")),
    "Ga" to Element("13221133122211332", listOf("Eu", "Ca", "Ac", "H", "Ca", "Zn")),
    "Ge" to Element("31131122211311122113222", listOf("Ho", "Ga")),
    "As" to Element("11131221131211322113322112", listOf("Ge", "Na")),
    "Se" to Element("13211321222113222112", listOf("As")),
    "Br" to Element("3113112211322112", listOf("Se")),
    "Kr" to Element("11131221222112", listOf("Br")),
    "Rb" to Element("1321122112", listOf("Kr")),
    "Sr" to Element("3112112", listOf("Rb")),
    "Y" to Element("1112133", listOf("Sr", "U")),
    "Zr" to Element("12322211331222113112211", listOf("Y", "H", "Ca", "Tc")),
    "Nb" to Element("1113122113322113111221131221", listOf("Er", "Zr")),
    "Mo" to Element("13211322211312113211", listOf("Nb")),
    "Tc" to Element("311322113212221", listOf("Mo")),
    "Ru" to Element("132211331222113112211", listOf("Eu", "Ca", "Tc")),
    "Rh" to Element("311311222113111221131221", listOf("Ho", "Ru")),
    "Pd" to Element("111312211312113211", listOf("Rh")),
    "Ag" to Element("132113212221", listOf("Pd")),
    "Cd" to Element("3113112211", listOf("Ag")),
    "In" to Element("11131221", listOf("Cd")),
    "Sn" to Element("13211", listOf("In")),
    "Sb" to Element("3112221", listOf("Pm", "Sn")),
    "Te" to Element("1322113312211", listOf("Eu", "Ca", "Sb")),
    "I" to Element("311311222113111221", listOf("Ho", "Te")),
    "Xe" to Element("11131221131211", listOf("I")),
    "Cs" to Element("13211321", listOf("Xe")),
    "Ba" to Element("311311", listOf("Cs")),
    "La" to Element("11131", listOf("Ba")),
    "Ce" to Element("1321133112", listOf("La", "H", "Ca", "Co")),
    "Pr" to Element("31131112", listOf("Ce")),
    "Nd" to Element("111312", listOf("Pr")),
    "Pm" to Element("132", listOf("Nd")),
    "Sm" to Element("311332", listOf("Pm", "Ca", "Zn")),
    "Eu" to Element("1113222", listOf("Sm")),
    "Gd" to Element("13221133112", listOf("Eu", "Ca", "Co")),
    "Tb" to Element("3113112221131112", listOf("Ho", "Gd")),
    "Dy" to Element("111312211312", listOf("Tb")),
    "Ho" to Element("1321132", listOf("Dy")),
    "Er" to Element("311311222", listOf("Ho", "Pm")),
    "Tm" to Element("11131221133112", listOf("Er", "Ca", "Co")),
    "Yb" to Element("1321131112", listOf("Tm")),
    "Lu" to Element("311312", listOf("Yb")),
    "Hf" to Element("11132", listOf("Lu")),
    "Ta" to Element("13112221133211322112211213322113", listOf("Hf", "Pa", "H", "Ca", "W")),
    "W" to Element("312211322212221121123222113", listOf("Ta")),
    "Re" to Element("111312211312113221133211322112211213322113", listOf("Ge", "Ca", "W")),
    "Os" to Element("1321132122211322212221121123222113", listOf("Re")),
    "Ir" to Element("3113112211322112211213322113", listOf("Os")),
    "Pt" to Element("111312212221121123222113", listOf("Ir")),
    "Au" to Element("132112211213322113", listOf("Pt")),
    "Hg" to Element("31121123222113", listOf("Au")),
    "Tl" to Element("111213322113", listOf("Hg")),
    "Pb" to Element("123222113", listOf("Tl")),
    "Bi" to Element("3113322113", listOf("Pm", "Pb")),
    "Po" to Element("1113222113", listOf("Bi")),
    "At" to Element("1322113", listOf("Po")),
    "Rn" to Element("311311222113", listOf("Ho", "At")),
    "Fr" to Element("1113122113", listOf("Rn")),
    "Ra" to Element("132113", listOf("Fr")),
    "Ac" to Element("3113", listOf("Ra")),
    "Th" to Element("1113", listOf("Ac")),
    "Pa" to Element("13", listOf("Th")),
    "U" to Element("3", listOf("Pa"))
)
val SORTED_ELEMENTS = CONWAY_MAP.values.sortedByDescending { it.sequence.length }

fun Element.decay(): List<Element> {
    return decaysTo.map { CONWAY_MAP[it]!! }
}

data class Node(val element: Element, val depth: Int)

fun conwaySolve(str: String, depth: Int = 1): Long {
    if (depth == 0) {
        return str.length.toLong()
    }

    val parts: MutableList<Element> = getConwayDecayed(str) ?: return nonConwaySolve(str, depth)

    val cache: MutableMap<Node, Long> = mutableMapOf()
    return parts.sumOf { e -> conwaySolve(Node(e, depth), cache) }
}

fun getConwayDecayed(str: String): MutableList<Element>? {
    val parts: MutableList<Element> = mutableListOf()

    var i = 0
    while (i != str.length) {
        val part = SORTED_ELEMENTS.firstOrNull { str.regionMatches(i, it.sequence, 0, it.sequence.length) }
        if (part == null) {
            return null
        }

        i += part.sequence.length
        parts += part
    }
    if (!isValidConwayDecayList(parts)) {
        return null
    }
    return parts


}

fun isValidConwayDecayList(parts: List<Element>): Boolean {
    return parts.windowed(2).all { it[0].sequence.last() != it[1].sequence.first() }
}

private fun nonConwaySolve(str: String, depth: Int): Long {
    val newStr = REGEX.findAll(str).map { it.value }.joinToString("") { "${it.length}${it.first()}" }
    return conwaySolve(newStr, depth - 1)
}

fun conwaySolve(node: Node, cache: MutableMap<Node, Long> = mutableMapOf()): Long {
    if (node.depth == 0) {
        return node.element.sequence.length.toLong()
    }
    if (node in cache) {
        return cache[node]!!
    }
    val ret = node.element.decay().sumOf { conwaySolve(Node(it, node.depth - 1), cache) }
    cache[node] = ret
    return ret
}