package com.vitekkor.echobot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Body(val type: Type, val `object`: Object?, @JsonProperty("group_id") val groupId: Long) {
    enum class Type {
        @JsonProperty("confirmation")
        CONFIRMATION,
        @JsonProperty("message_new")
        MESSAGE_NEW
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Object(val message: Message)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Message(
        @JsonProperty("peer_id")
        val peerId: Int,
        @JsonProperty("random_id")
        val randomId: Long,
        val text: String,
        val attachments: ArrayList<Any>,
        @JsonProperty("fwd_messages")
        val forwardMessages: ArrayList<Any>
    )
}
