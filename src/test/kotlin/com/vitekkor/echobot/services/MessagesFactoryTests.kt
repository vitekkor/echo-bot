package com.vitekkor.echobot.services

import com.vitekkor.echobot.dto.Body
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import kotlin.random.Random


@RunWith(MockitoJUnitRunner::class)
class MessagesFactoryTests {

    @Mock
    private lateinit var messagesFactory: MessagesFactory

    @BeforeEach
    fun openMocks() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun createEchoMessageTest() {
        val messages = MutableList(100) {
            Body.Message(
                Random.nextInt(Int.MAX_VALUE),
                Random.nextLong(Long.MAX_VALUE),
                getRandomString(),
                arrayListOf(),
                arrayListOf()
            )
        }
        `when`(messagesFactory.createEchoMessage(any())).thenCallRealMethod()

        for (message in messages) {
            val echoMessage = messagesFactory.createEchoMessage(message)
            with(message) {
                assertThat(echoMessage)
                    .containsEntry("peer_id", listOf(peerId.toString()))
                    .doesNotContainEntry("random_id", listOf(randomId.toString()))
                    .containsEntry("message", listOf(text))
            }
        }
    }

    private fun getRandomString(): String {
        return ('a'..'z').asSequence().take(Random.nextInt(500)).joinToString("")
    }
}