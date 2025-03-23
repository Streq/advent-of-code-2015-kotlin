@file:Suppress("unused")

package util

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import java.util.logging.*
import kotlin.reflect.KClass


fun <T : Any> logger(clazz: KClass<T>, lvl: Level): Logger {
    return Logger.getLogger(clazz.java.name).apply {
        level = lvl
        useParentHandlers = false
        val fmt = object : Formatter() {
            override fun format(record: LogRecord?): String {
                return "${record?.level}: ${record?.message}\n"
            }
        }
        val plainFmt = object : Formatter() {
            override fun format(record: LogRecord?): String {
                return "${record?.message}\n"
            }
        }

        fun upTo(l: Level): Filter = Filter { r: LogRecord -> r.level.intValue() < l.intValue() }
        fun handler(fmt: Formatter, from: Level, until: Level) = ConsoleHandler().apply {
            formatter = fmt
            level = from
            filter = upTo(until)
        }
        addHandler(handler(plainFmt, Level.ALL, Level.WARNING))
        addHandler(handler(fmt, Level.WARNING, Level.SEVERE))
        addHandler(handler(fmt, Level.SEVERE, Level.OFF))

    }
}

fun <T, R : Comparable<R>> List<T>.filterMax(selector: (T) -> R): Pair<List<T>, R?> {
    val temp = this.map { it to selector(it) }
    val maxValue: R = temp.maxOfOrNull { it.second } ?: return Pair(listOf(), null)
    return Pair(temp.filter { it.second == maxValue }.map { it.first }, maxValue)
}

fun <K, V, R : Comparable<R>> Map<K, V>.filterMax(selector: (Map.Entry<K, V>) -> R): Pair<Map<K, V>, R?> {
    val temp = this.mapValues { it.value to selector(it) }
    val maxValue: R = temp.maxOfOrNull { it.value.second } ?: return Pair(emptyMap(), null)
    return Pair(temp.filter { it.value.second == maxValue }.mapValues { it.value.first }, maxValue)
}

fun Logger.log(level: Level, any: Any) {
    log(level) { any.toString() }
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
    info { "starting at $start" }
    val ret = solver()
    val end = Instant.now()
    info { "ended at $end" }
    val diff = Duration.between(start, end)
    severe { "this shit took me $diff" }
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