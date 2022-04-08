package com.vitekkor.echobot.configs

import com.vitekkor.echobot.dto.LongPollServer
import com.vitekkor.echobot.dto.asLongPollServer
import com.vitekkor.echobot.services.MessagesFactory
import com.vitekkor.echobot.services.VkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.integrationFlow
import org.springframework.messaging.Message

@Configuration
class LongPollConfig(
    private val vkApi: VkApi,
    private val vkApiConfig: VkApiConfig,
    private val messagesFactory: MessagesFactory
) {
    @Bean
    fun longPollServer(): LongPollServer = runBlocking {
        CoroutineScope(Dispatchers.IO).async {
            val response = vkApi.makePostRequest(
                VkApi.Methods.GET_LONG_POLL_SERVER,
                messagesFactory.getLongPollMessage(vkApiConfig.groupId)
            )
            return@async response?.response?.asLongPollServer() ?: throw IllegalStateException()
        }.await()
    }

    @Bean
    fun messagesFlow() = integrationFlow("messagesInputChannel") {
        route<Message<*>> { it.headers["channel"] }
    }
}