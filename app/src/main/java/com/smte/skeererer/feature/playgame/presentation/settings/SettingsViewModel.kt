package com.smte.skeererer.feature.playgame.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smte.skeererer.core.update
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        SettingsUiState(
            soundState = settingsRepository.currentSoundState
        )
    )
    val uiState: State<SettingsUiState> = _uiState

    private var setSoundJob: Job? = null
    private var fetchSoundJob: Job? = null

    init {
        fetchSettings()
    }

    fun changeSoundState(enabled: Boolean) {
        setSoundJob?.cancel()
        setSoundJob = viewModelScope.launch {
            settingsRepository.setSoundState(enabled)
        }
    }

    private fun fetchSettings() {
        fetchSoundJob?.cancel()
        fetchSoundJob = settingsRepository.getSoundState()
            .onEach { soundState ->
                _uiState.update { it.copy(soundState = soundState) }
            }
            .catch { e ->
                e.printStackTrace()
            }
            .launchIn(viewModelScope)
    }
}