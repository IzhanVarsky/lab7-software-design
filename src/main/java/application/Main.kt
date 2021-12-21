package application

import application.profiled.First
import application.profiled.Second

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val first = First()
        val second = Second()

        repeat(100) {
            println("Iteration: $it")

            with(first) {
                method1("")
                method1("", 0)
                method2(0, 0)
                method3("")
            }

            with(second) {
                method1("")
                method1("", 0)
                method2(0, 0)
                method3("")
            }
        }
    }
}