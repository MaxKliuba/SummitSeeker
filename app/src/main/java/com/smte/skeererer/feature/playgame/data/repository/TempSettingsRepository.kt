package com.smte.skeererer.feature.playgame.data.repository

import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TempSettingsRepository @Inject constructor() : SettingsRepository {

    private var soundState = MutableStateFlow(true)

    override fun getSoundState(): StateFlow<Boolean> = soundState

    override suspend fun setSoundState(enabled: Boolean) {
        soundState.value = enabled
    }
}