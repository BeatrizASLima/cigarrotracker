package com.example.cigarrotracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [EstatisticasEntity::class, HistoricoEntity::class],
    version = 2,
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
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `historico` (`diaIndex` INTEGER NOT NULL, `cigarros` INTEGER NOT NULL, PRIMARY KEY(`diaIndex`))"
                )
            }
        }
    }
}
