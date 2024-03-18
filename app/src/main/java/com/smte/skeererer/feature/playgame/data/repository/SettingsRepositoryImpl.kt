package com.smte.skeererer.feature.playgame.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingsRepository {

    private val Context.dataStore by preferencesDataStore(name = "sound_settings")
    private var soundState: Boolean = true

    override val currentSoundState: Boolean
        get() = soundState

    override fun getSoundState(): Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SOUND_STATE_KEY] ?: true
    }.onEach { soundState = it }

    override suspend fun setSoundState(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SOUND_STATE_KEY] = enabled
        }
    }

    companion object {
        val SOUND_STATE_KEY = booleanPreferencesKey("sound_state")
    }
}