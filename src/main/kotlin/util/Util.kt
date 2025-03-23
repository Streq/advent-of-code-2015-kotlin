@file:Suppress("unused")

package util

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import kotlin.reflect.KClass

fun <T : Any>logger(clazz: KClass<T>, lvl: Level): Logger {
    return Logger.getLogger(clazz.java.name).apply {
        level = lvl
        // Remove default parent handlers
        useParentHandlers = false

        // Remove default parent handlers
        useParentHandlers = false

        // Attach a new ConsoleHandler
        val handler = ConsoleHandler().apply {
            level = Level.ALL // Allow all logs to be printed
            formatter = SimpleFormatter() // Optional: Format log output
        }

        addHandler(handler) // Register the new handler
    }
}

fun Logger.log(level: Level, any: Any) {
    log(level, any.toString())
}

fun Logger.severe(any: Any) {
    log(Level.SEVERE, any)
}

fun Logger.warning(any: Any) {
    log(Level.WARNING, any)
}

fun Logger.info(any: Any) {
    log(Level.INFO, any)
}

fun Logger.config(any: Any) {
    log(Level.CONFIG, any)
}

fun Logger.fine(any: Any) {
    log(Level.FINE, any)
}

fun Logger.finer(any: Any) {
    log(Level.FINER, any)
}

fun Logger.finest(any: Any) {
    log(Level.FINEST, any)
}

fun <T> Logger.timeSolution(solver: () -> T): T {
    val start = Instant.now()
    info("starting at $start")
    val ret = solver()
    val end = Instant.now()
    info("ended at $end")
    val diff = Duration.between(start, end)
    info("this shit took me $diff")
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