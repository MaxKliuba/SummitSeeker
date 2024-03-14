package com.smte.skeererer.feature.playgame.domain.model

data class GameState(
    val isRunning: Boolean,
    val backgroundOffset1: Int,
    val backgroundOffset2: Int,
    val score: Int,
    val player: Player,
    val artifacts: List<Artifact>,
)