package com.smte.skeererer.feature.playgame.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameScoreDao {

    @Query("SELECT * FROM scores")
    fun getScores(): Flow<List<GameScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScores(vararg scores: GameScoreEntity)

    @Query("DELETE FROM scores")
    suspend fun deleteAll()
}