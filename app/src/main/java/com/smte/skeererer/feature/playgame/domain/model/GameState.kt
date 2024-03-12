package com.smte.skeererer.feature.playgame.domain.model

data class GameState(
    val isRunning: Boolean,
    val player: Player,
    val artifacts: List<Artifact>,
)