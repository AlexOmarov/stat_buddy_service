package ru.berte.statbuddyservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatBuddyServiceApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<StatBuddyServiceApplication>(*args)
}
