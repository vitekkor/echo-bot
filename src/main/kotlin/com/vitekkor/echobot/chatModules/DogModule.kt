package com.vitekkor.echobot.chatModules

import com.vitekkor.echobot.dto.Body
import com.vitekkor.echobot.services.VkApi
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class DogModule : ChatModule(
    channelName = "/пёс"
) {
    override fun process(body: Body) {
        runBlocking {
            val processedBody = LinkedMultiValueMap<String, String>()
            processedBody.add("peer_id", body.`object`!!.message.peerId.toString())
            processedBody.add("random_id", System.currentTimeMillis().toString())
            processedBody.add("message", "Cам ты пёс")
            vkApi.value.makePostRequest(VkApi.Methods.MESSAGES_SEND, processedBody)
        }
    }
}