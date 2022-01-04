package com.vitekkor.echobot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EchoBotApplication

fun main(args: Array<String>) {
    runApplication<EchoBotApplication>(*args)
}
