package ru.berte.statbuddyservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
class StatBuddyServiceApplication

fun main(args: Array<String>) {
    // enable tracing propagation between different reactive pipelines
    Hooks.enableAutomaticContextPropagation()
    @Suppress("SpreadOperator")
    runApplication<StatBuddyServiceApplication>(*args)
}
