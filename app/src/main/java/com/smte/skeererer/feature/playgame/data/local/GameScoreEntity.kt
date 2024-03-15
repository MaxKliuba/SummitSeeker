package com.smte.skeererer.feature.playgame.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class GameScoreEntity(
    val score: Int,
    @PrimaryKey val timestamp: Long,
)