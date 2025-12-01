package com.example.cigarrotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estatisticas")
data class EstatisticasEntity(
    @PrimaryKey
    val id: Int = 1,               // sempre 1, só temos um registo
    val cigarrosTotal: Int,
    val diasDeUso: Int
)
