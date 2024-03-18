package com.smte.skeererer.feature.playgame.presentation.ratings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smte.skeererer.core.update
import com.smte.skeererer.feature.playgame.domain.usecase.GetScores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RatingsViewModel @Inject constructor(
    private val getScoresUseCase: GetScores,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        RatingsUiState(scores = emptyList())
    )
    val uiState: State<RatingsUiState> = _uiState

    private var fetchScoresJob: Job? = null

    init {
        fetchScores()
    }

    private fun fetchScores() {
        fetchScoresJob?.cancel()
        fetchScoresJob = getScoresUseCase()
            .onEach { scores ->
                _uiState.update { it.copy(scores = scores) }
            }
            .catch {
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }
}