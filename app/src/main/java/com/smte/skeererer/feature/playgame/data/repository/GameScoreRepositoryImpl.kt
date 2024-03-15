package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.data.local.GameScoreDao
import com.smte.skeererer.feature.playgame.data.local.toGameScore
import com.smte.skeererer.feature.playgame.data.local.toGameScoreEntity
import com.smte.skeererer.feature.playgame.domain.model.GameScore
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameScoreRepositoryImpl @Inject constructor(
    private val gameScoreDao: GameScoreDao,
) : GameScoreRepository {

    override fun getScores(): Flow<List<GameScore>> =
        gameScoreDao.getScores()
            .map { entities ->
                entities.map { it.toGameScore() }
            }

    override suspend fun addScore(score: GameScore) {
        gameScoreDao.addScores(score.toGameScoreEntity())
    }

    override suspend fun clearScores() {
        gameScoreDao.deleteAll()
    }
}