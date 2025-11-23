package com.example.cigarrotracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CigarroViewModel : ViewModel() {

    var cigarrosHoje by mutableStateOf(0)
        private set

    var cigarrosTotal by mutableStateOf(0)
        private set

    var diasDeUso by mutableStateOf(1)
        private set

    var precoMaco by mutableStateOf(6.0)
    var cigarrosPorMaco by mutableStateOf(20)

    fun adicionarCigarro() {
        cigarrosHoje++
        cigarrosTotal++
    }

    fun removerCigarro() {
        if (cigarrosHoje > 0) {
            cigarrosHoje--
            cigarrosTotal--
            if (cigarrosTotal < 0) cigarrosTotal = 0
        }
    }

    fun fecharDia() {
        diasDeUso++
        cigarrosHoje = 0
    }

    val mediaPorDia: Double
        get() = if (diasDeUso == 0) 0.0 else cigarrosTotal.toDouble() / diasDeUso

    val dinheiroGasto: Double
        get() = if (cigarrosPorMaco == 0) 0.0
        else (cigarrosTotal.toDouble() / cigarrosPorMaco) * precoMaco
}
