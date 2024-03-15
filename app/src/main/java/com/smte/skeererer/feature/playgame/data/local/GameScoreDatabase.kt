package com.smte.skeererer.feature.playgame.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GameScoreEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class GameScoreDatabase : RoomDatabase() {
    abstract val gameScoreDao: GameScoreDao

    companion object {
        private const val DATABASE_NAME = "scores_db"

        @Volatile
        private var INSTANCE: GameScoreDatabase? = null

        fun getInstance(context: Context): GameScoreDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GameScoreDatabase::class.java,
                    DATABASE_NAME,
                )
                    .build()
                    .also { INSTANCE = it }
            }
    }
}