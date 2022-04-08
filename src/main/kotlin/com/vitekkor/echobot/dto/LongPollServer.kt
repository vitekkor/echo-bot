package com.vitekkor.echobot.dto

data class LongPollServer(val key: String, val server: String, val ts: Int)

fun Map<String, String>.asLongPollServer(): LongPollServer? {
    val key = get("key") ?: return null
    val server = get("server") ?: return null
    val ts = get("ts")?.toInt() ?: return null
    return LongPollServer(key, server, ts)
}
