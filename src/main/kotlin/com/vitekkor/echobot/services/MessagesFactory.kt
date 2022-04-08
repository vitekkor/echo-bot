package com.vitekkor.echobot.services

import com.vitekkor.echobot.dto.Body
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
class MessagesFactory {
    fun createEchoMessage(original: Body.Message): MultiValueMap<String, String> {
        val message = LinkedMultiValueMap<String, String>()
        message.add("peer_id", original.peerId.toString())
        message.add("random_id", System.currentTimeMillis().toString())
        message.add("message", original.text)
        return message
    }

    fun getLongPollMessage(groupId: String): MultiValueMap<String, String> {
        val message = LinkedMultiValueMap<String, String>()
        message.add("group_id", groupId)
        return message
    }
}