package com.smte.skeererer.feature.playgame.domain.repository

import com.smte.skeererer.feature.playgame.domain.model.GameScore
import kotlinx.coroutines.flow.Flow

interface GameScoreRepository {

    fun getScores(): Flow<List<GameScore>>

    suspend fun addScore(score: GameScore)

    suspend fun clearScores()
}