package com.party.shared.message

data class GameMessage(
    val type: String,
    val roomCode: String,
    val userId: String?,
    val payload: Any?
)