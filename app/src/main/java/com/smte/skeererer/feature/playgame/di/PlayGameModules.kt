package com.smte.skeererer.feature.playgame.di

import com.smte.skeererer.feature.playgame.data.repository.Mp3SoundRepository
import com.smte.skeererer.feature.playgame.data.repository.TempGameScoreRepository
import com.smte.skeererer.feature.playgame.data.repository.TempSettingsRepository
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameScoreRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGameScoreRepository(
        tempGameScoreRepository: TempGameScoreRepository
    ): GameScoreRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSettingsRepository(
        tempSettingsRepository: TempSettingsRepository
    ): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SoundRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSoundRepository(
        mp3SoundRepository: Mp3SoundRepository
    ): SoundRepository
}