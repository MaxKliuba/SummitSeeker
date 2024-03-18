package com.smte.skeererer.feature.playgame.data.repository

import android.content.Context
import android.media.SoundPool
import com.smte.skeererer.R
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Mp3SoundRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val settingsRepository: SettingsRepository,
) : SoundRepository {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()
    private val jumpSoundId = soundPool.load(context, R.raw.jump, 1)
    private val successSoundId = soundPool.load(context, R.raw.success, 1)
    private val gameOverSoundId = soundPool.load(context, R.raw.game_over, 1)

    override fun playJumpSound() {
        if (settingsRepository.getSoundState().value) {
            soundPool.play(jumpSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    override fun playSuccessSound() {
        if (settingsRepository.getSoundState().value) {
            soundPool.play(successSoundId, 1f, 1f, 1, 0, 1f)
        }
    }

    override fun playGameOverSound() {
        if (settingsRepository.getSoundState().value) {
            soundPool.play(gameOverSoundId, 1f, 1f, 2, 0, 1f)
        }
    }
}