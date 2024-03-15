package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.model.Artifact
import com.smte.skeererer.feature.playgame.domain.model.ArtifactType
import com.smte.skeererer.feature.playgame.domain.model.GameBackground
import com.smte.skeererer.feature.playgame.domain.model.GameState
import com.smte.skeererer.feature.playgame.domain.model.Player
import com.smte.skeererer.feature.playgame.domain.repository.PlayGameController
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
            background = GameBackground(
                offset1 = 0,
                offset2 = fieldWidth,
                sizeX = fieldWidth,
                sizeY = fieldHeight
            ),
            score = 0,
            player = Player(
                x = playerX,
                y = playerInitialY,
                sizeX = playerSizeX,
                sizeY = step - 1,
            ),
            artifacts = emptyList(),
        )

        while (true) {
            gameState = if (pause) {
                gameState.copy(isRunning = false)
            } else {
                val player =
                    if (playerJumpCounter >= AFTER_JUMP_DELAY && gameState.player.y != step) {
                        gameState.player.copy(y = step)
                    } else if (playerJumpCounter == 0 && gameState.player.y != playerInitialY) {
                        gameState.player.copy(y = playerInitialY)
                    } else gameState.player

                val artifacts = gameState.artifacts.map { artifact ->
                    artifact.copy(x = artifact.x - pixelSpeed)
                }.toMutableList()

                val lastArtifact = artifacts.maxByOrNull { it.x }

                if ((lastArtifact != null
                            && Random.nextInt(200 / pixelSpeed) == 0
                            && lastArtifact.x + lastArtifact.sizeX < fieldWidth - artifactSize)
                    || lastArtifact == null
                ) {
                    artifacts += Artifact(
                        x = fieldWidth,
                        y = if (Random.nextInt(2) == 0) artifactLowY else artifactHighY,
                        sizeX = (step / 2),
                        sizeY = (step / 2),
                        type = ArtifactType.entries[Random.nextInt(ArtifactType.entries.size)],
                    )
                }

                val score = artifacts.sumOf { artifact ->
                    if (player.hasCollisionWith(artifact)) {
                        soundRepository.playSuccessSound()
                        artifact.type.score
                    } else 0
                }

                artifacts.removeAll { artifact ->
                    artifact.x <= -artifact.sizeX * 2 || player.hasCollisionWith(artifact)
                }

                println(artifacts.size)

                if (playerJumpCounter > 0) {
                    playerJumpCounter = max(0, playerJumpCounter - pixelSpeed)
                }

                GameState(
                    isRunning = true,
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