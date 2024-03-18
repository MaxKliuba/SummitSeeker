package com.smte.skeererer.feature.playgame.domain.repository

interface SoundRepository {

    fun playJumpSound()

    fun playSuccessSound()

    fun playGameOverSound()
}