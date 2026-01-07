package com.example.cigarrotracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.round

@Composable
fun Ecra02(viewModel: CigarroViewModel) {
    val media = viewModel.mediaPorDia
    val mediaArredondada = (round(media * 10) / 10.0)
    val historico = viewModel.historicoDiario
    val valoresBrutos = historico.map { it.cigarros.toFloat() }
    val valoresGrafico = if (valoresBrutos.isEmpty()) {
        listOf(0f)
    } else {
        val semZeroFinal = valoresBrutos.dropLastWhile { it == 0f }
        if (semZeroFinal.isEmpty()) listOf(0f) else semZeroFinal
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Estatísticas",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = "Vê a tua evolução ao longo dos dias.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Evolucao (historico)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SimpleLineChart(
                    values = valoresGrafico,
                    labelFormatter = { value -> value.toInt().toString() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Text(
                    text = "Dados reais por dia com base no historico gravado.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                )
            }
        }

        StatCard(
            titulo = "Total fumado",
            valor = "${viewModel.cigarrosTotal} cigarros"
        )

        StatCard(
            titulo = "Dias de uso da app",
            valor = "${viewModel.diasDeUso}"
        )

        StatCard(
            titulo = "Média por dia",
            valor = "$mediaArredondada cigarros/dia"
        )
    }
}

@Composable
private fun StatCard(
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = SolidColor(
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
