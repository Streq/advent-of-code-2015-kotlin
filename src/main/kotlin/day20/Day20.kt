package day20

import util.*
import java.util.logging.Level
import java.util.logging.Logger

val LOG: Logger = logger(Day20::class, Level.FINEST)

typealias InputType = Long
typealias OutputType = Long

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String) = inputFromRawFile(getFileAsString(path))
private fun inputFromRawFile(contents: String) = contents.toLong()


class Day20 {

    object Part1 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solve(34000000))
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

        private fun solveInternal(presentThreshold: InputType): OutputType {
            return naiveMethod(presentThreshold)
        }

        private fun naiveMethod(presentsRequired: Long): Long {
            var house = 0L
            var presents = 0L
            while (presents < presentsRequired) {
                house += 2*3*4*5*7
                presents = getPresents(house)
                //LOG.fine { "house $house -> $presents presents" }
            }
            return house
        }

        fun getPresents(house: Long): Long {
            return getDivisorSum2(house) * 10
        }

        fun getDivisorSum(house: Long): Long {
            var ret = 0L
            for (i in 1..house / 2) {
                if (house % i == 0L) {
                    ret += i
                }
            }
            return ret + house
        }

        fun getDivisorSum2(house: Long): Long {

            var ret = 1L
            var maxDivisor = house
            var i = 2L
            while (i <= house) {
                if (house % i == 0L) {
                    ret += i
                    maxDivisor = house / i
                    if (maxDivisor > house/2) {
                        ret += maxDivisor
                        continue
                    }
                }
                ++i
            }
            LOG.fine { "house $house has $ret divisorSum" }
            return ret
        }

        fun getDivisors(house: Long): Long {
            var min = 2L
            var max = house

            val uniques = mutableSetOf(1L)
            val primes = mutableListOf(1L)
            while (min <= max) {
                for (i in min..max) {
                    if (max % i == 0L) {
                        primes += i
                        uniques += uniques.map { it * i }
                        min = i
                        max = max / i
                        break
                    }
                }
            }
            val sum = uniques.sum()
            LOG.finer { "$house -> $sum\n\t${primes}\n\t${uniques.sorted()}" }
            return sum
        }

        fun getDivisorsDebug(house: Long): Pair<Long, Set<Long>> {
            var min = 2L
            var max = house

            val uniques = mutableSetOf(1L)
            while (min <= max) {
                for (i in min..max) {
                    if (max % i == 0L) {
                        uniques += uniques.map { it * i }
                        min = i
                        max = max / i
                        break
                    }
                }
            }
            val sum = uniques.sum()
            LOG.finer { "$house -> $sum\n\t${uniques.sorted()}" }
            return sum to uniques
        }

        // 180:
        // 1-----------.---.
        // 2-----.---. 3-. 5
        // 2---. 3-. 5 3 5
        // 3-. 5 3 5   5
        // 3 5   5
        // 5

    }

    object Part2 {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solve(34000000))
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
            return naiveMethod(input)
        }


        private fun naiveMethod(presentsRequired: Long): Long {
            val divisorSumRequired = presentsRequired
            var house = 0L
            var presents = 0L
            while (presents < divisorSumRequired) {
                house += 2 * 3 * 2 * 7
                presents = getPresents(house)
                LOG.fine { "house $house -> $presents presents" }
            }
            return house
        }

        fun getPresents(house: Long): Long {
            return getDivisors(house) * 11
        }

        fun getDivisors(house: Long): Long {
            var min = 2L
            var max = house

            val uniques = mutableSetOf(1L)
            val primes = mutableListOf(1L)
            while (min <= max) {
                for (i in min..max) {
                    if (max % i == 0L) {

                        primes += i
                        uniques += uniques.map { it * i }

                        min = i
                        max = max / i
                        break
                    }
                }
            }
            val sum = uniques.filter { house / it <= 50 }.sum()
            LOG.finer { "$house -> $sum\n\t${primes}\n\t${uniques.sorted()}" }
            return sum
        }
    }
}