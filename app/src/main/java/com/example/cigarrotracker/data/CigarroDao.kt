package com.example.cigarrotracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CigarroDao {

    @Query("SELECT * FROM estatisticas WHERE id = 1")
    suspend fun getEstatisticas(): EstatisticasEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarEstatisticas(est: EstatisticasEntity)
}
