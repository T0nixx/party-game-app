package com.party.wear.rps

import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult
import com.party.shared.rps.repository.RpsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class RpsRepositoryImpl @Inject constructor() : RpsRepository {
    override suspend fun sendChoice(choice: RpsChoice): RpsResult {
        delay(1000) // simulate network latency

        val allChoices = RpsChoice.entries.toTypedArray()
        val winning = allChoices.random()
        val isWinner = when (choice) {
            RpsChoice.ROCK -> winning == RpsChoice.SCISSORS
            RpsChoice.PAPER -> winning == RpsChoice.ROCK
            RpsChoice.SCISSORS -> winning == RpsChoice.PAPER
        }

        return RpsResult(userChoice = winning, isWinner = isWinner)
    }
}