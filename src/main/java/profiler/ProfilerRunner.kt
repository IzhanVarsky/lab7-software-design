package profiler

import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()

    val config = Files.readAllLines(Path.of("config.txt"))
        .map { it.split("""\s""".toRegex()).getOrElse(1) { "" } }

    DemoAspect.profiledPackage = config[0]
    val mainClassName = config[1]

    Class.forName(mainClassName)
        .getMethod("main", Array<String>::class.java)
        .invoke(null, args)

    DemoAspect.profiler.printStatistics()

    println("Summary time: ${System.currentTimeMillis() - startTime} ms")
}