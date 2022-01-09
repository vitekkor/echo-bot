package com.vitekkor.echobot.services

import com.vitekkor.echobot.configs.VkApiConfig
import com.vitekkor.echobot.dto.VkResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchangeOrNull
import java.net.URI

@Service
class VkApi(private val vkApiConfig: VkApiConfig, private val client: WebClient) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getApiUri(method: Methods): URI =
        URI("https://api.vk.com/method/${method}?access_token=${vkApiConfig.accessToken}&v=${vkApiConfig.version}")


    enum class Methods(private val methodName: String) {
        MESSAGES_SEND("messages.send");

        override fun toString(): String = methodName
    }

    suspend fun makePostRequest(method: Methods, body: MultiValueMap<String, String>): VkResponse? {
        val request = client.post().uri(getApiUri(method))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(body))
        log.info("Request: method - $method; body - $body")

        return request.awaitExchangeOrNull {
            if (it.statusCode().is2xxSuccessful) {
                val vkResponse = it.awaitBody<VkResponse>()
                if (vkResponse.error != null) {
                    log.info("Something went wrong: $vkResponse")
                } else {
                    log.info("Successful request! Response: $vkResponse")
                }
                return@awaitExchangeOrNull vkResponse
            } else {
                log.error("Unexpected error: ${it.statusCode()}; ${it.awaitBody<String>()}")
                return@awaitExchangeOrNull null
            }
        }
    }
}