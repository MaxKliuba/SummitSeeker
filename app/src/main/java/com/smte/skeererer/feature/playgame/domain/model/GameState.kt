package com.smte.skeererer.feature.playgame.domain.model

data class GameState(
    val isGameOver: Boolean,
    val isRunning: Boolean,
    val background: GameBackground,
    val score: Int,
    val player: Player,
    val artifacts: List<Artifact>,
)