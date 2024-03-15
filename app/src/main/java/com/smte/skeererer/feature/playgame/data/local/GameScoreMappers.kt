package com.smte.skeererer.feature.playgame.data.local

import com.smte.skeererer.feature.playgame.domain.model.GameScore

fun GameScore.toGameScoreEntity(): GameScoreEntity =
    GameScoreEntity(
        score = score,
        timestamp = timestamp,
    )

fun GameScoreEntity.toGameScore(): GameScore =
    GameScore(
        score = score,
        timestamp = timestamp,
    )