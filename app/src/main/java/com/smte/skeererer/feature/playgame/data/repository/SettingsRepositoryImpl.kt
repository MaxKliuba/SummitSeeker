package com.smte.skeererer.feature.playgame.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SettingsRepository {

    private val sharedPreferences =
        context.getSharedPreferences("sound_settings", Context.MODE_PRIVATE)

    override fun getSoundState(): StateFlow<Boolean> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == SOUND_STATE_KEY) {
                    sharedPreferences?.getBoolean(key, true)?.let { soundState ->
                        trySend(soundState)
                    }
                }
            }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Lazily,
        initialValue = sharedPreferences.getBoolean(SOUND_STATE_KEY, true),
    )

    override fun setSoundState(enabled: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(SOUND_STATE_KEY, enabled)
            apply()
        }
    }

    companion object {
        const val SOUND_STATE_KEY = "sound_state"
    }
}