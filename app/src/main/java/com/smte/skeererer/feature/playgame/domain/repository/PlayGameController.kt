package com.smte.skeererer.feature.playgame.domain.repository

import com.smte.skeererer.feature.playgame.domain.model.GameState
import kotlinx.coroutines.flow.Flow

interface PlayGameController {

    fun play(fieldWidth: Int, fieldHeight: Int): Flow<GameState>

    fun applyPlayerJump()

    fun pause()

    fun resume()
}