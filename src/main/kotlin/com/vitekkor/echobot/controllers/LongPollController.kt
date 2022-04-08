package com.vitekkor.echobot.controllers

import com.vitekkor.echobot.dto.LongPollServer
import com.vitekkor.echobot.services.MessageGateway
import com.vitekkor.echobot.services.VkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LongPollController(
    longPollServer: LongPollServer,
    private val vkApi: VkApi,
    private val messagesGateway: MessageGateway
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val log = LoggerFactory.getLogger(this::class.java)

    private val server = longPollServer.server
    private val key = longPollServer.key
    private var ts = longPollServer.ts

    @PostConstruct
    fun startLongPoll() {
        coroutineScope.launch {
            while (true) {
                val updates = vkApi.getUpdates(server, key, ts)
                if (updates != null) {
                    ts = updates.ts
                    updates.updates.forEach {
                        val text = it.`object`!!.message.text
                        val command = text.split(" ").getOrNull(0)
                        if (command != null) {
                            if (command.startsWith("/")) {
                                try {
                                    messagesGateway.send(it, command)
                                } catch (_: NoSuchBeanDefinitionException) {
                                    log.info("No handlers for command $command")
                                }
                            } else {
                                messagesGateway.send(it, "echoInputChannel")
                            }
                        }
                    }
                }
                delay(1000)
            }
        }
    }
}