package com.vitekkor.echobot.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationPropertiesBinding
@ConfigurationProperties(prefix = "vk.api")
@ConstructorBinding
data class VkApiConfig(
    val accessToken: String,
    val confirmationString: String,
    val groupId: String,
    val version: Double
)
