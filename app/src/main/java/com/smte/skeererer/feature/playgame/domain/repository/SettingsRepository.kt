package com.smte.skeererer.feature.playgame.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun getSoundState(): StateFlow<Boolean>

    suspend fun setSoundState(enabled: Boolean)
}