package com.example.cigarrotracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

@Composable
fun Ecra02(viewModel: CigarroViewModel) {
    val media = viewModel.mediaPorDia
    val mediaArredondada = (round(media * 10) / 10.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text("ESTATÍSTICAS", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Total fumado: ${viewModel.cigarrosTotal}")
        Text("Dias de uso: ${viewModel.diasDeUso}")
        Text("Média por dia: $mediaArredondada")
    }
}
