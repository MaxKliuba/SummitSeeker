package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.GameScore
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TempGameScoreRepository @Inject constructor() : GameScoreRepository {
    private val tempScores = MutableStateFlow<List<GameScore>>(
        emptyList()
    )

    override fun getScores(): Flow<List<GameScore>> = tempScores

    override suspend fun addScore(score: GameScore) {
        tempScores.value += score
    }

    override suspend fun clearScores() {
        tempScores.value = emptyList()
    }
}