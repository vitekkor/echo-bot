package com.vitekkor.echobot.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class EchoBotConfig {
    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }
}
