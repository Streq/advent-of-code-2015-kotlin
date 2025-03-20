package day09

import util.getFileAsString
import util.inputPath
import util.timeSolution

object Day09 {
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
}

data class Connection(val distance: Int, val a: String, val b: String)
typealias InputType = List<Connection>
typealias OutputType = String
typealias DistanceMap = Map<String, Map<String, Int>>

object Input {
    val PATH: String = inputPath(object {}.javaClass)
}

private fun inputFromFile(path: String): InputType {
    return inputFromRawFile(getFileAsString(path))
}

private fun inputFromRawFile(contents: String): InputType {
    val regex = """(\w+) to (\w+) = (\d+)""".toRegex()
    return contents.lineSequence()
        .mapNotNull { regex.matchEntire(it)?.destructured }
        .map { (a, b, distance) -> Connection(distance.toInt(), a, b) }
        .toList()
}

private fun solveInternal(input: InputType): OutputType {
    val map: DistanceMap = distanceMap(input)
    val paths =
        map.keys.asSequence()
            .flatMap { getPathsThatCoverAllCitiesOnce(map, it) }
            .map { it to getTotalDistance(it, map) }
            .sortedBy { it.first.toString() }
            .sortedByDescending { it.second }
            .distinctBy { it.second }.toList()


    return listOf(paths.first(), paths.last())
        .joinToString("\n") { pair ->
            "${pair.first.joinToString(" -> ") { it }} = ${pair.second}"
        }
}

private fun distanceMap(connections: InputType): Map<String, Map<String, Int>> {
    val map: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()
    connections.forEach {
        map.getOrPut(it.a) { mutableMapOf() }[it.b] = it.distance
        map.getOrPut(it.b) { mutableMapOf() }[it.a] = it.distance
    }
    return map.mapValues { it.value.toMap() }.toMap()
}

private fun getTotalDistance(path: List<String>, map: DistanceMap): Int {
    return path.windowed(2).sumOf { map[it[0]]!![it[1]]!! }
}

private fun getPathsThatCoverAllCitiesOnce(
    map: DistanceMap,
    startingCity: String
): Collection<List<String>> {
    val allCities = map.keys
    val paths: MutableList<List<String>> = mutableListOf()

    data class Node(val city: String, val pathAlreadyTraversed: List<String>)

    val toVisit: ArrayDeque<Node> = ArrayDeque()

    toVisit.addLast(Node(startingCity, listOf()))

    while (toVisit.isNotEmpty()) {
        with(toVisit.removeLast()) {
            val currentPath = pathAlreadyTraversed + city
            val visitedCities = currentPath.toSet()
            val connectedCities = map[city]!!.keys
            val pathsToTake = connectedCities.minus(visitedCities).map { Node(it, currentPath) }
            toVisit += pathsToTake
            if (pathsToTake.isEmpty() && allCities == visitedCities) {
                paths += currentPath
            }
        }
    }
    return paths
}
