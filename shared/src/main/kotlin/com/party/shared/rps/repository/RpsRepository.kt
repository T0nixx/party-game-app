package com.party.shared.rps.repository

import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult

interface RpsRepository {
    suspend fun sendChoice(choice: RpsChoice): RpsResult
}