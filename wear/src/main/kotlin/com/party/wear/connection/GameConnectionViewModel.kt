package com.party.wear.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.party.shared.message.GameMessage
import com.party.wear.network.GameWebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameConnectionViewModel @Inject constructor() : ViewModel() {

    private var socket: GameWebSocketClient? = null
    private val mapper = jacksonObjectMapper()

    private val _messageFlow = MutableSharedFlow<GameMessage>()
    val messageFlow: SharedFlow<GameMessage> = _messageFlow

    fun connect(serverUrl: String, roomCode: String, userId: String?) {
        socket = GameWebSocketClient(
            serverUrl = serverUrl,
            roomCode = roomCode,
            userId = userId,
            onMessage = { emitMessage(it) },
            onFailure = { emitError(it) }
        )
        socket?.connect()
        send("join", null)
    }

    fun send(type: String, payload: Any?) {
        socket?.send(type, payload)
    }

    fun close() {
        socket?.close()
    }

    private fun emitMessage(message: GameMessage) {
        viewModelScope.launch { _messageFlow.emit(message) }
    }

    private fun emitError(e: Throwable) {
        viewModelScope.launch {
            _messageFlow.emit(GameMessage("error", "", "", e.message))
        }
    }
}
