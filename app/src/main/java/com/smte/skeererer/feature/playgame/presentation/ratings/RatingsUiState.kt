package com.smte.skeererer.feature.playgame.presentation.ratings

import com.smte.skeererer.feature.playgame.domain.model.GameScore

data class RatingsUiState(
    val scores: List<GameScore>,
)