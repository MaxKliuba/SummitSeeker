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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val scoreRepository: GameScoreRepository,
) : ViewModel() {
    private val playGameController = LocalPlayGameController()

    private val _uiState: MutableState<PlayUiState> = mutableStateOf(PlayUiState(gameState = null))
    val uiState: State<PlayUiState> = _uiState

    fun applyJump() {
        playGameController.applyPlayerJump()
    }

    fun pauseGame() {
        playGameController.pause()
    }

    fun resumeGame() {
        playGameController.resume()
    }

    fun playGame(fieldWidth: Int, fieldHeight: Int) {
        playGameController.play(fieldWidth, fieldHeight)
            .onEach { gameState ->
                _uiState.update { it.copy(gameState = gameState) }
            }
            .catch { e ->
                e.printStackTrace()
            }
            .launchIn(viewModelScope)
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