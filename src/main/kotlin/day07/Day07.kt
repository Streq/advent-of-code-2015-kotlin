package day07

import util.getFileAsString
import util.inputPath
import util.timeSolution

typealias InputType = Map<String, Expr>
typealias OutputType = String

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}


sealed interface Expr
sealed interface Value : Expr
data class LiteralValue(val value: UShort) : Value
data class Reference(val ref: String) : Value
data class BinaryOperator(val operation: String, val a: Value, val b: Value) : Expr
data class UnaryOperator(val operation: String, val a: Value) : Expr


private fun parseValue(expr: String): Value {
    return if ("\\d+".toRegex().matchEntire(expr) != null) {
        LiteralValue(expr.toUShort())
    } else {
        Reference(expr)
    }
}

private fun parseExpr(expr: String): Expr {
    val split = expr.split(" ")
    return when (split.size) {
        1 -> parseValue(split[0])
        2 -> UnaryOperator(split[0], parseValue(split[1]))
        else -> BinaryOperator(split[1], parseValue(split[0]), parseValue(split[2]))
    }
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    return contents.lines().filterNot { it.isBlank() }.map { it.split(" -> ") }.associate { it[1] to parseExpr(it[0]) }
}

private fun solveAllValues(input: InputType): InputType {
    val map = input.toMutableMap()

    fun unary(operator: String, value: UShort): UShort {
        return when (operator) {
            "NOT" -> value.inv()
            else -> throw IllegalStateException()
        }
    }

    fun binary(operator: String, a: UShort, b: UShort): UShort {
        return when (operator) {
            "AND" -> a.and(b)
            "OR" -> a.or(b)
            "LSHIFT" -> a.toInt().shl(b.toInt()).toUShort()
            "RSHIFT" -> a.toInt().ushr(b.toInt()).toUShort()
            else -> throw IllegalStateException()
        }
    }


    fun resolveExpression(v: Expr): UShort {
        val realValue = when (v) {
            is LiteralValue -> v.value
            is Reference -> {
                val ret = resolveExpression(map[v.ref]!!)
                map[v.ref] = LiteralValue(ret)
                ret
            }

            is BinaryOperator -> binary(v.operation, resolveExpression(v.a), resolveExpression(v.b))
            is UnaryOperator -> unary(v.operation, resolveExpression(v.a))
        }
        return realValue
    }
    for (key in map.keys) {
        map[key] = LiteralValue(resolveExpression(map[key]!!))
    }


    return map.toMap()
}

private fun toString(input: InputType): String {
    return input.entries.filter { it.value is LiteralValue }.map { it.key to (it.value as LiteralValue).value }
        .sortedBy { it.first }
        .joinToString("\n") { "${it.first}: ${it.second}" }
}

class Day07 {

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
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            return toString(solveAllValues(input))
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
            return timeSolution { solveInternal(input) }
        }

        private fun solveInternal(input: InputType): OutputType {
            val firstSolution = solveAllValues(input)
            val secondMap = input.toMutableMap()
            secondMap["b"] = firstSolution["a"]!!
            return toString(solveAllValues(secondMap))
        }
    }
}