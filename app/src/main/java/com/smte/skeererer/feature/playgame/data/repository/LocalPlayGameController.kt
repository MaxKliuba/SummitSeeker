package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.Artifact
import com.smte.skeererer.feature.playgame.domain.model.ArtifactType
import com.smte.skeererer.feature.playgame.domain.model.GameState
import com.smte.skeererer.feature.playgame.domain.model.Player
import com.smte.skeererer.feature.playgame.domain.repository.PlayGameController
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import kotlin.math.max
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class LocalPlayGameController(
    private val soundRepository: SoundRepository,
) : PlayGameController {
    private var pause: Boolean = false
    private var playerJumpCounter: Int = 0

    override fun play(fieldWidth: Int, fieldHeight: Int): Flow<GameState> = flow {
        resume()
        playerJumpCounter = 0

        val delay = 5
        val pixelSpeed = 5
        val stepCount = 3
        val step = fieldHeight / stepCount

        val playerX = step / 5
        val playerInitialY = (step * 1.9f).toInt()
        val playerSizeX = (step * 21f / 37).toInt()

        val artifactSize = step / 2
        val artifactLowY = playerInitialY + artifactSize / 2
        val artifactHighY = step + artifactSize / 2

        var gameState = GameState(
            isRunning = true,
            backgroundOffset1 = 0,
            backgroundOffset2 = fieldWidth,
            score = 0,
            player = Player(
                id = UUID.randomUUID(),
                x = playerX,
                y = playerInitialY,
                sizeX = playerSizeX,
                sizeY = step - 1,
            ),
            artifacts = listOf(
                Artifact(
                    id = UUID.randomUUID(),
                    x = fieldWidth,
                    y = artifactLowY,
                    sizeX = (step / 2),
                    sizeY = (step / 2),
                    type = ArtifactType.COIN,
                )
            ),
        )

        while (true) {
            gameState = if (pause) {
                gameState.copy(isRunning = false)
            } else {
                val player = if (playerJumpCounter >= AFTER_JUMP_DELAY) {
                    gameState.player.copy(y = step)
                } else {
                    gameState.player.copy(y = playerInitialY)
                }

                val artifacts = gameState.artifacts.map { artifact ->
                    artifact.copy(x = artifact.x - pixelSpeed)
                }
                    .filter { it.x >= -it.sizeX * 2 }
                    .toMutableList()

                artifacts.maxByOrNull { it.x }?.let { lastArtifact ->
                    val probability = Random.nextInt(200 / pixelSpeed) == 0
                    if (probability && lastArtifact.x + lastArtifact.sizeX < fieldWidth - artifactSize) {
                        artifacts += Artifact(
                            id = UUID.randomUUID(),
                            x = fieldWidth,
                            y = if (Random.nextInt(2) == 0) artifactLowY else artifactHighY,
                            sizeX = (step / 2),
                            sizeY = (step / 2),
                            type = ArtifactType.entries[Random.nextInt(ArtifactType.entries.size)],
                        )
                    }
                }

                val score = artifacts.filter { artifact ->
                    player.hasCollisionWith(artifact).also { hasCollision ->
                        if (hasCollision) {
                            soundRepository.playSuccessSound()
                        }
                    }
                }.sumOf { artifact ->
                    artifact.type.score
                }

                artifacts.removeAll { artifact ->
                    player.hasCollisionWith(artifact)
                }

                if (playerJumpCounter > 0) {
                    playerJumpCounter = max(0, playerJumpCounter - pixelSpeed)
                }

                GameState(
                    isRunning = true,
                    backgroundOffset1 = if (gameState.backgroundOffset1 <= -fieldWidth + pixelSpeed) {
                        fieldWidth
                    } else {
                        gameState.backgroundOffset1 - pixelSpeed
                    },
                    backgroundOffset2 = if (gameState.backgroundOffset2 <= -fieldWidth + pixelSpeed) {
                        fieldWidth
                    } else {
                        gameState.backgroundOffset2 - pixelSpeed
                    },
                    score = gameState.score + score,
                    player = player,
                    artifacts = artifacts,
                )
            }

            emit(gameState)
            delay(delay.milliseconds)
        }
    }.flowOn(Dispatchers.IO)

    override fun pause() {
        pause = true
    }

    override fun resume() {
        pause = false
    }

    override fun applyPlayerJump() {
        if (playerJumpCounter == 0) {
            soundRepository.playJumpSound()
            playerJumpCounter = JUMP_DURATION + AFTER_JUMP_DELAY
        }
    }

    companion object {
        private const val JUMP_DURATION = 500
        private const val AFTER_JUMP_DELAY = 200
    }
}