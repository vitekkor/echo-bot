package com.vitekkor.echobot.controllers

import com.vitekkor.echobot.configs.VkApiConfig
import com.vitekkor.echobot.dto.Body
import com.vitekkor.echobot.services.MessagesFactory
import com.vitekkor.echobot.services.VkApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono
import kotlin.random.Random

@WebFluxTest(MainController::class)
class MainControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var vkApiConfig: VkApiConfig

    @MockBean
    private lateinit var messagesFactory: MessagesFactory

    @MockBean
    private lateinit var vkApi: VkApi


    @Test
    fun confirmationTest() {
        val confirmationBody = Body(Body.Type.CONFIRMATION, null, 27108)
        webTestClient.post()
            .uri("/")
            .body(Mono.just(confirmationBody), Body::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo(vkApiConfig.confirmationString)
    }

    @Test
    fun echoMessageTest() {
        val messages = MutableList(100) {
            Body.Message(
                Random.nextInt(Int.MAX_VALUE),
                Random.nextLong(Long.MAX_VALUE),
                getRandomString(),
                arrayListOf(),
                arrayListOf()
            )
        }

        for (message in messages) {
            val messageBody = Body(Body.Type.MESSAGE_NEW, Body.Object(message), 27108)
            webTestClient.post()
                .uri("/")
                .body(Mono.just(messageBody), Body::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody<String>()
                .isEqualTo("OK")
        }
    }

    private fun getRandomString(): String {
        return ('a'..'z').asSequence().take(Random.nextInt(500)).joinToString("")
    }
}