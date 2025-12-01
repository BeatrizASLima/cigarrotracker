package com.example.cigarrotracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EstatisticasEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CigarroDatabase : RoomDatabase() {

    abstract fun cigarroDao(): CigarroDao

    companion object {
        @Volatile
        private var INSTANCE: CigarroDatabase? = null

        fun getInstance(context: Context): CigarroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CigarroDatabase::class.java,
                    "cigarro_database"
                ).fallbackToDestructiveMigration() // simples para agora
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
