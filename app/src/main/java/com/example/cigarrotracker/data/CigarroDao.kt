package com.example.cigarrotracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cigarrotracker.data.HistoricoEntity

@Dao
interface CigarroDao {

    @Query("SELECT * FROM estatisticas WHERE id = 1")
    suspend fun getEstatisticas(): EstatisticasEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarEstatisticas(est: EstatisticasEntity)

    @Query("SELECT * FROM historico ORDER BY diaIndex DESC LIMIT :limit")
    suspend fun getHistorico(limit: Int): List<HistoricoEntity>

    @Query("SELECT * FROM historico WHERE diaIndex = :dia")
    suspend fun getHistoricoPorDia(dia: Int): HistoricoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvarHistorico(est: HistoricoEntity)
}
