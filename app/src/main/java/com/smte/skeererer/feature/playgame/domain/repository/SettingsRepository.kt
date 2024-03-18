package com.smte.skeererer.feature.playgame.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val currentSoundState: Boolean

    fun getSoundState(): Flow<Boolean>

    suspend fun setSoundState(enabled: Boolean)
}