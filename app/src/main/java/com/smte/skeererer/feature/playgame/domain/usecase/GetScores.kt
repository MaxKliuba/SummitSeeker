package com.smte.skeererer.feature.playgame.domain.usecase

import com.smte.skeererer.feature.playgame.domain.model.GameScore
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetScores @Inject constructor(
    private val gameScoreRepository: GameScoreRepository
) {
    operator fun invoke(limit: Int = 10): Flow<List<GameScore>> =
        gameScoreRepository.getScores()
            .map { scores ->
                scores.sortedByDescending { it.score }
                    .take(limit)
            }
}