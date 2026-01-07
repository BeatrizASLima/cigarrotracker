package com.example.cigarrotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historico")
data class HistoricoEntity(
    @PrimaryKey
    val diaIndex: Int,
    val cigarros: Int
)
