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


class Grid<T>(w: Int, h: Int, initializer: (Int, Int) -> T) {
    val width: Int = w
    val height: Int = h
    val array: Array<Any?> = Array(this.width * this.height) { i -> initializer(i / this.width, i % this.height) }

    fun set(x: Int, y: Int, v: T) {
        array[i(x, y)] = v
    }

    fun setSafe(x: Int, y: Int, v: T): Boolean {
        if (withinBounds(i(x, y))) {
            array[i(x, y)] = v
            return true
        }
        return false
    }

    fun get(x: Int, y: Int): T {
        return getRaw(i(x, y))
    }

    fun getOrElse(x: Int, y: Int, supplier: () -> T): T {
        val i = i(x, y)
        return if (withinBounds(i)) getRaw(i) else supplier.invoke()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getRaw(i: Int) = array[i] as T

    private fun withinBounds(i: Int) = i >= 0 && i < array.size

    private fun i(x: Int, y: Int) = y * width + x
}