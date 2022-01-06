package com.vitekkor.echobot.controllers

import com.vitekkor.echobot.configs.VkApiConfig
import com.vitekkor.echobot.dto.Body
import com.vitekkor.echobot.services.MessagesFactory
import com.vitekkor.echobot.services.VkApi
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.awaitExchangeOrNull

@Controller
class MainController(
    private val vkApiConfig: VkApiConfig,
    private val vkApi: VkApi,
    private val client: WebClient,
    private val messagesFactory: MessagesFactory
) {
    @PostMapping("/")
    @ResponseBody
    suspend fun request(@RequestBody body: Body): String {
        if (body.type == Body.Type.CONFIRMATION) {
            return vkApiConfig.confirmationString
        }
        if (body.type == Body.Type.MESSAGE_NEW) {
            val echoMessage = messagesFactory.createEchoMessage(body.`object`!!.message)
            val c = client.post().uri(vkApi.getApiUri(VkApi.Methods.MESSAGES_SEND))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData(echoMessage))
                c.awaitExchangeOrNull {
                    val result = it.awaitBodyOrNull<String>()
                    println(result)
                }
        }
        return "OK"
    }
}