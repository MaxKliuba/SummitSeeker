package com.smte.skeererer.feature.playgame.presentation.play

import com.smte.skeererer.feature.playgame.domain.model.GameState

sealed class PlayUiState {
    data object Start : PlayUiState()
    data class Play(
        val fieldWidth: Int,
        val fieldHeight: Int,
        val gameState: GameState
    ) : PlayUiState()

    data object GameOver : PlayUiState()
    data object Pause : PlayUiState()
}