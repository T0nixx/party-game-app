package com.party.wear.network

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.party.shared.message.GameMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class GameWebSocketClient(
    private val serverUrl: String,
    private val roomCode: String,
    private val userId: String?,
    private val onMessage: (GameMessage) -> Unit,
    private val onFailure: (Throwable) -> Unit = {},
    private val onOpen: () -> Unit = {}
) {
    private lateinit var webSocket: WebSocket
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()
    private val mapper = jacksonObjectMapper()

    fun connect() {
        val request = Request.Builder()
            .url(serverUrl)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.e("WebSocket", "연결 성공")
                onOpen()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val message = mapper.readValue<GameMessage>(text)
                onMessage(message)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "연결 실패: ${t.message}", t)
                onFailure(t)
            }
        })
    }

    fun send(type: String, payload: Any?) {
        val msg = GameMessage(type, roomCode, userId, payload)
        val json = mapper.writeValueAsString(msg)
        webSocket.send(json)
    }

    fun close() {
        webSocket.close(1000, "bye")
    }
}
