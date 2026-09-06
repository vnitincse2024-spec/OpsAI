package com.example.opsai.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncidentWebSocketManager @Inject constructor(
    private val client: OkHttpClient
) {
    private var webSocket: WebSocket? = null

    fun connect(url: String, listener: IncidentWebSocketListener) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Normal closure")
        webSocket = null
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }
}
