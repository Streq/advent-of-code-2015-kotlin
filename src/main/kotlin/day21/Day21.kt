package day21

import util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.max

val LOG: Logger = logger(Day21::class, Level.FINE)

data class Fighter(val hp: Int, val damage: Int, val armor: Int)

val BOSS = Fighter(103, 9, 2)


data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

val RINGS = listOf(
    Item("Nothing", 0, 0, 0),
    Item("Nothing", 0, 0, 0),
    Item("Damage +1", 25, 1, 0),
    Item("Damage +2", 50, 2, 0),
    Item("Damage +3", 100, 3, 0),
    Item("Defense +1", 20, 0, 1),
    Item("Defense +2", 40, 0, 2),
    Item("Defense +3", 80, 0, 3),
)
val ARMOR = listOf(
    Item("Nothing", 0, 0, 0),
    Item("Leather", 13, 0, 1),
    Item("Chainmail", 31, 0, 2),
    Item("Splintmail", 53, 0, 3),
    Item("Bandedmail", 75, 0, 4),
    Item("Platemail", 102, 0, 5),
)
val WEAPONS = listOf(
    Item("Dagger", 8, 4, 0),
    Item("Shortsword", 10, 5, 0),
    Item("Warhammer", 25, 6, 0),
    Item("Longsword", 40, 7, 0),
    Item("Greataxe", 74, 8, 0),
)

typealias InputType = Fighter
typealias OutputType = Pair<Int, Int>

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}


class Day21 {

    object BothParts {
        @JvmStatic
        fun main(args: Array<String>) {
            println(solve(BOSS))
        }


        fun solve(input: InputType): OutputType {
            LOG.fine(input)
            return LOG.timeSolution { solveInternal(input) }
        }

        private fun solveInternal(boss: InputType): OutputType {
            var minCostWin = Int.MAX_VALUE
            var maxCostLose = Int.MIN_VALUE
            for (weapon in WEAPONS) {
                for ((i, ring) in RINGS.withIndex()) {
                    for (ring2 in RINGS.subList(i + 1, RINGS.size)) {
                        for (armor in ARMOR) {
                            val cost = weapon.cost + ring.cost + ring2.cost + armor.cost

                            val me = Fighter(
                                100,
                                weapon.damage + ring.damage + ring2.damage,
                                armor.armor + ring.armor + ring2.armor
                            )

                            if (wins(me, boss)) {
                                if (minCostWin > cost) {
                                    LOG.fine { "$me WINS for $cost coins (w:${weapon.name}, r1:${ring.name}, r2:${ring2.name}, a:${armor.name}" }
                                    minCostWin = cost
                                }
                            } else {
                                if (maxCostLose < cost) {
                                    LOG.fine { "$me dies awfully for $cost coins (w:${weapon.name}, r1:${ring.name}, r2:${ring2.name}, a:${armor.name}" }
                                    maxCostLose = cost
                                }
                            }

                        }
                    }
                }
            }

            return minCostWin to maxCostLose
        }

        private fun wins(me: Fighter, boss: Fighter): Boolean {
            val dmgDealtByMe = max(me.damage - boss.armor, 1)
            val dmgDealtByBoss = max(boss.damage - me.armor, 1)
            LOG.finest { "me:$dmgDealtByMe, boss:$dmgDealtByBoss" }
            val bossHP = boss.hp
            val meHP = me.hp

            val turnsItTakesMeToWin = bossHP / dmgDealtByMe + if (bossHP % dmgDealtByMe == 0) {
                0
            } else {
                1
            }
            val turnsItTakesBossToWin = meHP / dmgDealtByBoss + if (meHP % dmgDealtByBoss == 0) {
                0
            } else {
                1
            }

            LOG.finest { "takes me: $turnsItTakesMeToWin, takes boss: $turnsItTakesBossToWin" }

            return turnsItTakesMeToWin <= turnsItTakesBossToWin
        }
    }
}