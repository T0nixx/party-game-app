package com.party.shared.rps.state

import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult

data class RpsUiState(
    val myChoice: RpsChoice? = null,
    val result: RpsResult? = null,
    val isLoading: Boolean = false
)