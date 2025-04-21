package com.party.shared.rps.state

import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult

data class RpsUiState(
    val gameType: String = "",
    val myChoice: RpsChoice? = null,
    val result: RpsResult? = null,
    val startAt: Long = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)