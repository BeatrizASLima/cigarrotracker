package com.example.cigarrotracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cigarrotracker.data.CigarroDatabase
import com.example.cigarrotracker.data.CigarroRepository
import kotlinx.coroutines.launch

class CigarroViewModel(application: Application) : ViewModel() {

    // --- ESTADO DO APP (igual ao que você já tinha) ---

    var cigarrosHoje: Int = 0
        private set

    var cigarrosTotal: Int = 0
        private set

    var diasDeUso: Int = 1
        private set

    var precoMaco: Double = 6.0
    var cigarrosPorMaco: Int = 20

    // --- REPOSITÓRIO / BD ---

    private val repository: CigarroRepository

    init {
        val db = CigarroDatabase.getInstance(application)
        repository = CigarroRepository(db.cigarroDao())

        // Carregar valores da BD quando o ViewModel é criado
        viewModelScope.launch {
            val est = repository.carregarEstatisticas()
            if (est != null) {
                cigarrosTotal = est.cigarrosTotal
                diasDeUso = est.diasDeUso
            } else {
                // primeira vez: grava valores iniciais
                salvarEstatisticasNaBd()
            }
        }
    }

    private fun salvarEstatisticasNaBd() {
        viewModelScope.launch {
            repository.salvarEstatisticas(cigarrosTotal, diasDeUso)
        }
    }

    // --- FUNÇÕES DE NEGÓCIO ---

    fun adicionarCigarro() {
        cigarrosHoje++
        cigarrosTotal++
        salvarEstatisticasNaBd()
    }

    fun removerCigarro() {
        if (cigarrosHoje > 0) {
            cigarrosHoje--
            cigarrosTotal--
            if (cigarrosTotal < 0) cigarrosTotal = 0
            salvarEstatisticasNaBd()
        }
    }

    fun fecharDia() {
        diasDeUso++
        cigarrosHoje = 0
        salvarEstatisticasNaBd()
    }

    val mediaPorDia: Double
        get() = if (diasDeUso == 0) 0.0 else cigarrosTotal.toDouble() / diasDeUso

    val dinheiroGasto: Double
        get() = if (cigarrosPorMaco == 0) 0.0
        else (cigarrosTotal.toDouble() / cigarrosPorMaco) * precoMaco
}

// Factory para poder passar Application ao ViewModel
class CigarroViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CigarroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CigarroViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
