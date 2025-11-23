package com.example.cigarrotracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destino(val route: String, val icon: ImageVector, val title: String) {
    object Ecra01 : Destino(
        route = "ecra01",
        icon = Icons.Default.Home,
        title = "Hoje"
    )
    object Ecra02 : Destino(
        route = "ecra02",
        icon = Icons.Default.BarChart,
        title = "Estatísticas"
    )
    object Ecra03 : Destino(
        route = "ecra03",
        icon = Icons.Default.AttachMoney,
        title = "Dinheiro"
    )

    companion object {
        val toList = listOf(Ecra01, Ecra02, Ecra03)
    }
}
