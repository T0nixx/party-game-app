package com.party.shared.rps.model

data class RpsResult(
    val userChoice: RpsChoice,
    val isWinner: Boolean,
)
