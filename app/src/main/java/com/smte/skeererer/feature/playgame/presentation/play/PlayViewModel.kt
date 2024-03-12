package com.smte.skeererer.feature.playgame.presentation.play

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smte.skeererer.feature.playgame.data.repository.LocalPlayGameController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor() : ViewModel() {
    private val playGameController = LocalPlayGameController()

    private val _uiState: MutableState<PlayUiState> = mutableStateOf(PlayUiState.Start)
    val uiState: State<PlayUiState> = _uiState

    fun applyJump() {
        playGameController.applyPlayerJump()
    }

    fun pauseGame() {
        _uiState.value = PlayUiState.Pause
        playGameController.pause()
    }

    fun restartGame() {
        _uiState.value = PlayUiState.Start
    }

    fun playGame(fieldWidth: Int, fieldHeight: Int) {
        when (_uiState.value) {
            is PlayUiState.Start -> {
                playGameController.play(fieldWidth, fieldHeight)
                    .onEach { gameState ->
                        _uiState.value = if (gameState.isRunning) {
                            PlayUiState.Play(
                                fieldWidth = fieldWidth,
                                fieldHeight = fieldHeight,
                                gameState = gameState,
                            )
                        } else {
                            PlayUiState.GameOver
                        }
                    }
                    .catch { e ->
                        e.printStackTrace()
                    }
                    .launchIn(viewModelScope)
            }

            is PlayUiState.Pause -> {
                playGameController.resume()
            }

            else -> {}
        }
    }
}