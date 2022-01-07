package com.vitekkor.echobot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VkResponse(val response: Any?, val error: Error?) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Error(
        @JsonProperty("error_code") val errorCode: Int,
        @JsonProperty("error_msg") val errorMessage: String,
        @JsonProperty("request_params") val requestParams: ArrayList<Map<String, String>>
    )
}
