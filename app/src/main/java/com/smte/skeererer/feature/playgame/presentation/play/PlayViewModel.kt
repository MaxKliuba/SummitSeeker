package com.smte.skeererer.feature.playgame.presentation.play

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class PlayViewModel @Inject constructor() : ViewModel() {

    val _uiState = mutableIntStateOf(1)

    fun moveLeft() {
        _uiState.intValue = max(0, _uiState.intValue - 1)
    }

    fun moveRight() {
        _uiState.intValue = min(2, _uiState.intValue + 1)
    }
}