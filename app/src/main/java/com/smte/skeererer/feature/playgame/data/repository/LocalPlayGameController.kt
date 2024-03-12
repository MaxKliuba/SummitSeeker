package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.Artifact
import com.smte.skeererer.feature.playgame.domain.model.GameState
import com.smte.skeererer.feature.playgame.domain.model.Player
import com.smte.skeererer.feature.playgame.domain.repository.PlayGameController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class LocalPlayGameController @Inject constructor() : PlayGameController {
    private var pause: Boolean = false
    private var playerJump: Boolean = false

    override fun play(fieldWidth: Int, fieldHeight: Int): Flow<GameState> = flow {
        resume()
        playerJump = false

        val stepCount = 3
        val step = fieldHeight / stepCount

        var gameState = GameState(
            isRunning = true,
            player = Player(
                x = step,
                y = step * 2,
                sizeX = step,
                sizeY = step - 1,
            ),
            artifacts = listOf(
                Artifact(
                    x = fieldWidth,
                    y = step,
                    sizeX = step,
                    sizeY = step - 1,
                )
            ),
        )

        while (gameState.isRunning) {
            if (pause) {
                delay(100.milliseconds)
                continue
            }

            val player = if (playerJump) {
                gameState.player.copy(y = step)
            } else {
                gameState.player.copy(y = 2 * step)
            }

            val artifacts = gameState.artifacts.map { artifact ->
                artifact.copy(x = artifact.x - 10)
            }.filter { it.x > -it.sizeX }

            val gameOver = artifacts.any { barrier ->
                player.hasCollisionWith(barrier)
            }

            gameState = GameState(
                isRunning = !gameOver,
                player = player,
                artifacts = artifacts,
            )

            emit(gameState)
            delay(10.milliseconds)
        }
    }

    override fun pause() {
        pause = true
    }

    override fun resume() {
        pause = false
    }

    override fun applyPlayerJump() {
        playerJump = !playerJump
    }
}