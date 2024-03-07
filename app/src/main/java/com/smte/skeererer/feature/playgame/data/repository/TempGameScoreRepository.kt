package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.GameScore
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TempGameScoreRepository @Inject constructor() : GameScoreRepository {
    private val tempScores = MutableStateFlow<List<GameScore>>(
        listOf(
            GameScore(345, 435),
            GameScore(3345345, 4345),
            GameScore(344565, 4365),
            GameScore(34545, 465435),
            GameScore(1345, 434565),
            GameScore(34545, 44565435),
            GameScore(2345, 43546456455),
            GameScore(345645, 433465635),
            GameScore(3415, 4355645),
            GameScore(345654645, 43657565),
            GameScore(413345, 454635),
            GameScore(435345, 45685635),
            GameScore(3654445, 4546535),
            GameScore(3765745, 434575),
            GameScore(34565, 4335),
            GameScore(356745, 4356),
            GameScore(365745, 4385),
            GameScore(34455, 4935),
            GameScore(344555, 439875),
            GameScore(344575, 435675),
        )
    )

    override fun getScores(): Flow<List<GameScore>> = tempScores

    override suspend fun addScore(score: GameScore) {
        tempScores.value += score
    }

    override suspend fun clearScores() {
        tempScores.value = emptyList()
    }
}