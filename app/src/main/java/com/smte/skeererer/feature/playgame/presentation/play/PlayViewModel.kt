package com.smte.skeererer.feature.playgame.presentation.play

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smte.skeererer.core.update
import com.smte.skeererer.feature.playgame.data.repository.LocalPlayGameController
import com.smte.skeererer.feature.playgame.domain.model.GameScore
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    soundRepository: SoundRepository,
    private val scoreRepository: GameScoreRepository,
) : ViewModel() {
    private val playGameController = LocalPlayGameController(soundRepository)

    private val _uiState: MutableState<PlayUiState> = mutableStateOf(PlayUiState(gameState = null))
    val uiState: State<PlayUiState> = _uiState

    private var playJob: Job? = null

    fun applyJump() {
        playGameController.applyPlayerJump()
    }

    fun pauseGame() {
        playGameController.pause()
    }

    fun resumeGame() {
        playGameController.resume()
    }

    fun restartGame() {
        _uiState.value.gameState?.let { gameState ->
            startGame(gameState.background.sizeX, gameState.background.sizeY)
        }
    }

    fun startGame(fieldWidth: Int, fieldHeight: Int) {
        playJob?.cancel()
        playJob = viewModelScope.launch {
            playGameController.play(fieldWidth, fieldHeight)
                .collectLatest { gameState ->
                    _uiState.update { it.copy(gameState = gameState) }
                }
        }
    }

    fun saveScore() {
        _uiState.value.gameState?.score?.let { score ->
            viewModelScope.launch {
                scoreRepository.addScore(
                    GameScore(
                        score = score,
                        timestamp = Date().time
                    )
                )
            }
        }
    }
}