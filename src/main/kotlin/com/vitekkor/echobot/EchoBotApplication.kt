package com.vitekkor.echobot

import com.vitekkor.echobot.configs.VkApiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.vitekkor.echobot"])
@EnableConfigurationProperties(VkApiConfig::class)
class EchoBotApplication

fun main(args: Array<String>) {
    runApplication<EchoBotApplication>(*args)
}
