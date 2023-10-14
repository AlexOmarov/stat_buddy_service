package ru.berte.stat_buddy_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatBuddyServiceApplication

fun main(args: Array<String>) {
    runApplication<StatBuddyServiceApplication>(*args)
}
