package com.vitekkor.echobot.controllers

import com.vitekkor.echobot.configs.VkApiConfig
import com.vitekkor.echobot.dto.Body
import com.vitekkor.echobot.services.MessagesFactory
import com.vitekkor.echobot.services.VkApi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController(
    private val vkApiConfig: VkApiConfig,
    private val vkApi: VkApi,
    private val messagesFactory: MessagesFactory
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    @ResponseBody
    suspend fun request(@RequestBody body: Body): String {
        log.info("New request: $body")
        if (body.type == Body.Type.CONFIRMATION) {
            return vkApiConfig.confirmationString
        }
        if (body.type == Body.Type.MESSAGE_NEW) {
            val echoMessage = messagesFactory.createEchoMessage(body.`object`!!.message)
            vkApi.makePostRequest(VkApi.Methods.MESSAGES_SEND) {
                it.addAll(echoMessage)
            }
        }
        return "OK"
    }
}