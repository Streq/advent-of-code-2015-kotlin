package util

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant


fun <T> timeSolution(solver: () -> T): T {
    val start = Instant.now()
    println("starting at $start")
    val ret = solver()
    val end = Instant.now()
    println("ended at $end")
    val diff = Duration.between(start, end)
    println("this shit took me $diff")
    return ret
}

fun getFileAsString(path: String): String {
    try {
        return Files.readString(Paths.get(path))
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun inputPath(c: Class<*>): String {
    return getPath(c, "input.txt")
}


fun exampleInputPath(c: Class<*>): String {
    return getPath(c, "example.txt")
}

fun exampleInputPath(c: Class<*>, num: Int): String {
    return getPath(c, "example$num.txt")
}

private fun getPath(c: Class<*>, file: String): String {
    val resource: URL? = c.getResource(file)
    return resource?.path!!
}
