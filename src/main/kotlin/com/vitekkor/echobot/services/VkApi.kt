package com.vitekkor.echobot.services

import com.vitekkor.echobot.configs.VkApiConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI

@Component
class VkApi {
    @Autowired
    private lateinit var vkApiConfig: VkApiConfig

    fun getApiUri(method: Methods): URI =
        URI("https://api.vk.com/method/${method}?access_token=${vkApiConfig.accessToken}&v=${vkApiConfig.version}")


    enum class Methods(private val methodName: String) {
        MESSAGES_SEND("messages.send");

        override fun toString(): String = methodName
    }
}