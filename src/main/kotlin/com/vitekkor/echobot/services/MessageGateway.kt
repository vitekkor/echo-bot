package com.vitekkor.echobot.services

import com.vitekkor.echobot.dto.Body
import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.handler.annotation.Header

@MessagingGateway
interface MessageGateway {

    @Gateway(requestChannel = "messagesInputChannel")
    fun send(body: Body, @Header("channel") channel: String)
}