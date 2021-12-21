package profiler

import org.aspectj.lang.Signature
import java.util.*

data class Stat(var count: Int = 0, var totalTime: Long = 0)

class ProfilerStatistics {
    private val classToMethods: MutableMap<String, MutableSet<String>> = mutableMapOf()
    private val statMap: MutableMap<String, Stat> = mutableMapOf()

    fun enterMethod(signature: Signature) {
        val methodName = signatureToMethod(signature)
        classToMethods.getOrPut(signature.declaringType.name) { mutableSetOf() }.add(methodName)
        statMap.getOrPut(methodName) { Stat() }.count++
    }

    fun exitMethod(signature: Signature, time: Long) {
        statMap.getOrPut(signatureToMethod(signature)) { Stat() }.totalTime += time
    }

    fun printStatistics() {
        classToMethods.forEach { (className, s) ->
            println()

            var totalCnt = 0
            var totalTime = 0L

            val maxLenOfMethod = s.maxOf(String::length)
            val formatStr = "| %-${maxLenOfMethod}s | %-5s | %-10s | %-12s |"

            val header = formatStr.format("Method", "Count", "Total Time", "Average Time")
            val separator1 = "=".repeat(header.length)
            val separator2 = "—".repeat(header.length)

            println(separator1)
            println("| %-${header.length - 4}s |".format("Class: $className"))
            println(separator1)
            println(header)
            println(separator2)
            s.forEach { method ->
                statMap[method]?.let { stat ->
                    println(
                        formatStr.format(
                            method,
                            stat.count,
                            "${stat.totalTime} ms",
                            "%.2f ms".format(Locale.ENGLISH, stat.totalTime * 1.0 / stat.count),
                        )
                    )
                    totalCnt += stat.count
                    totalTime += stat.totalTime
                }
            }
            println(separator2)
            println(
                formatStr.format(
                    "Summary",
                    totalCnt,
                    "$totalTime ms",
                    "%.2f ms".format(Locale.ENGLISH, totalTime * 1.0 / totalCnt),
                )
            )
            println(separator2)
        }
    }

    private fun signatureToMethod(signature: Signature) = signature.toLongString()
}