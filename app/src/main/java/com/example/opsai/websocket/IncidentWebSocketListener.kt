package com.example.opsai.websocket

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class IncidentWebSocketListener(
    private val onMessageReceived: (String) -> Unit,
    private val onStatusChanged: (WebSocketStatus) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        onStatusChanged(WebSocketStatus.CONNECTED)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        onMessageReceived(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        onMessageReceived(bytes.utf8())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        onStatusChanged(WebSocketStatus.DISCONNECTED)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        onStatusChanged(WebSocketStatus.ERROR)
    }
}

enum class WebSocketStatus {
    CONNECTED, DISCONNECTED, RECONNECTING, ERROR
}
