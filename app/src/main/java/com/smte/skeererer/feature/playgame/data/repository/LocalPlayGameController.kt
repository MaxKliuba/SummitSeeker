package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.Artifact
import com.smte.skeererer.feature.playgame.domain.model.ArtifactType
import com.smte.skeererer.feature.playgame.domain.model.GameBackground
import com.smte.skeererer.feature.playgame.domain.model.GameState
import com.smte.skeererer.feature.playgame.domain.model.Player
import com.smte.skeererer.feature.playgame.domain.model.PlayerState
import com.smte.skeererer.feature.playgame.domain.repository.PlayGameController
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.math.max
import kotlin.random.Random

class LocalPlayGameController(
    private val soundRepository: SoundRepository,
) : PlayGameController {

    private var pause: Boolean = false
    private var playerJumpCounter: Int = 0

    override fun play(fieldWidth: Int, fieldHeight: Int): Flow<GameState> = flow {
        resume()
        playerJumpCounter = 0

        val period = 4
        val pixelSpeed = 4
        val stepCount = 3
        val step = fieldHeight / stepCount
        val bottomOffset = step / 8

        val playerX = step / 5
        val playerY = step - 1
        val jumpHeight = step
        val playerRunY = fieldHeight - playerY - bottomOffset
        val playerJumpY = playerRunY - jumpHeight
        val playerSizeX = (playerY * 21f / 37).toInt()

        val rockSize = (jumpHeight * 0.9f).toInt()
        val rockY = fieldHeight - rockSize - bottomOffset

        val artifactSize = jumpHeight / 2
        val artifactLowY = playerRunY + artifactSize / 2
        val artifactHighY = playerJumpY + artifactSize / 2

        var gameState = GameState(
            isGameOver = false,
            isRunning = true,
            background = GameBackground(
                offset1 = 0,
                offset2 = fieldWidth,
                sizeX = fieldWidth,
                sizeY = fieldHeight
            ),
            score = 0,
            player = Player(
                x = playerX,
                y = playerRunY,
                sizeX = playerSizeX,
                sizeY = playerY,
                state = PlayerState.RUN_RIGHT,
            ),
            artifacts = emptyList(),
        )

        var timer = System.currentTimeMillis()
        var runTimer = System.currentTimeMillis()

        while (!gameState.isGameOver) {
            if (System.currentTimeMillis() - timer < period) continue

            timer = System.currentTimeMillis()

            gameState = if (pause) {
                gameState.copy(isRunning = false)
            } else {
                val player =
                    if (playerJumpCounter >= AFTER_JUMP_DELAY) {
                        gameState.player.copy(
                            y = playerJumpY,
                            state = PlayerState.JUMP,
                        )
                    } else {
                        gameState.player.copy(
                            y = playerRunY,
                            state = if (System.currentTimeMillis() - runTimer >= period * 40) {
                                runTimer = System.currentTimeMillis()
                                when (gameState.player.state) {
                                    PlayerState.RUN_RIGHT -> PlayerState.RUN_LEFT
                                    PlayerState.RUN_LEFT -> PlayerState.RUN_RIGHT
                                    PlayerState.JUMP -> PlayerState.RUN_LEFT
                                }
                            } else {
                                gameState.player.state

                            },
                        )
                    }

                val artifacts = gameState.artifacts.map { artifact ->
                    artifact.copy(x = artifact.x - pixelSpeed)
                }.toMutableList()

                val lastArtifact = artifacts.maxByOrNull { it.x }

                if ((lastArtifact != null
                            && Random.nextInt(200 / pixelSpeed) == 0
                            && lastArtifact.x + lastArtifact.sizeX < fieldWidth - artifactSize * 2)
                    || lastArtifact == null
                ) {
                    val type = ArtifactType.entries[Random.nextInt(ArtifactType.entries.size)]

                    artifacts += Artifact(
                        x = fieldWidth,
                        y = when {
                            type == ArtifactType.ROCK -> rockY
                            Random.nextInt(2) == 0 -> artifactLowY
                            else -> artifactHighY
                        },
                        sizeX = if (type == ArtifactType.ROCK) rockSize else artifactSize,
                        sizeY = if (type == ArtifactType.ROCK) rockSize else artifactSize,
                        type = type,
                    )
                }

                val score = artifacts.sumOf { artifact ->
                    if (player.hasCollisionWith(artifact) && artifact.type != ArtifactType.ROCK) {
                        soundRepository.playSuccessSound()
                        artifact.type.score
                    } else 0
                }

                val isGameOver = artifacts.any { artifact ->
                    player.hasCollisionWith(artifact) && artifact.type == ArtifactType.ROCK
                }
                if (isGameOver) {
                    soundRepository.playGameOverSound()
                }

                artifacts.removeAll { artifact ->
                    artifact.x <= -artifact.sizeX * 2 || (player.hasCollisionWith(artifact) && artifact.type != ArtifactType.ROCK)
                }

                if (playerJumpCounter > 0) {
                    playerJumpCounter = max(0, playerJumpCounter - pixelSpeed)
                }

                GameState(
                    isGameOver = isGameOver,
                    isRunning = !isGameOver,
                    background = gameState.background.copy(
                        x = if (gameState.background.x <= -fieldWidth + pixelSpeed) {
                            fieldWidth
                        } else {
                            gameState.background.x - pixelSpeed
                        },
                        y = if (gameState.background.y <= -fieldWidth + pixelSpeed) {
                            fieldWidth
                        } else {
                            gameState.background.y - pixelSpeed
                        }
                    ),
                    score = gameState.score + score,
                    player = player,
                    artifacts = artifacts,
                )
            }

            emit(gameState)
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
        private const val JUMP_DURATION = 800
        private const val AFTER_JUMP_DELAY = 100
    }
}