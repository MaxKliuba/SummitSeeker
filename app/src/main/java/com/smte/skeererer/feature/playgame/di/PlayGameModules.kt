package com.smte.skeererer.feature.playgame.di

import android.content.Context
import com.smte.skeererer.feature.playgame.data.local.GameScoreDao
import com.smte.skeererer.feature.playgame.data.local.GameScoreDatabase
import com.smte.skeererer.feature.playgame.data.repository.GameScoreRepositoryImpl
import com.smte.skeererer.feature.playgame.data.repository.Mp3SoundRepository
import com.smte.skeererer.feature.playgame.data.repository.SettingsRepositoryImpl
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import com.smte.skeererer.feature.playgame.domain.repository.SettingsRepository
import com.smte.skeererer.feature.playgame.domain.repository.SoundRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameScoreRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGameScoreRepository(
        gameScoreRepositoryImpl: GameScoreRepositoryImpl
    ): GameScoreRepository
}

@Module
@InstallIn(SingletonComponent::class)
object GameScoreDatabaseModule {

    @Singleton
    @Provides
    fun provideGameScoreDatabase(@ApplicationContext context: Context): GameScoreDatabase =
        GameScoreDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideGameScoreDao(gameScoreDatabase: GameScoreDatabase): GameScoreDao =
        gameScoreDatabase.gameScoreDao
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
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