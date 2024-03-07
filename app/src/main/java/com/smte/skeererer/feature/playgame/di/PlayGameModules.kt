package com.smte.skeererer.feature.playgame.di

import com.smte.skeererer.feature.playgame.data.repository.TempGameScoreRepository
import com.smte.skeererer.feature.playgame.domain.repository.GameScoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NumFactsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGameScoreRepository(
        tempGameScoreRepository: TempGameScoreRepository
    ): GameScoreRepository
}