package com.example.cigarrotracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Ecra03(viewModel: CigarroViewModel) {
    val precoTexto = remember { mutableStateOf(viewModel.precoMaco.toString()) }
    val cigarrosTexto = remember { mutableStateOf(viewModel.cigarrosPorMaco.toString()) }
    val historico = viewModel.historicoDiario
    val gastoPorDia = historico.map { entrada ->
        val gasto = if (viewModel.cigarrosPorMaco == 0) {
            0f
        } else {
            (entrada.cigarros.toFloat() / viewModel.cigarrosPorMaco) * viewModel.precoMaco.toFloat()
        }
        entrada.diaIndex to gasto
    }
    val gastoPorMes = gastoPorDia
        .groupBy { (diaIndex, _) -> ((diaIndex - 1) / 30) + 1 }
        .mapValues { (_, itens) -> itens.sumOf { it.second.toDouble() }.toFloat() }
        .toSortedMap()
    val mesAtual = ((viewModel.diasDeUso - 1) / 30) + 1
    val totalMesesMostrar = 6
    val primeiroMes = (mesAtual - totalMesesMostrar + 1).coerceAtLeast(1)
    val meses = (primeiroMes..mesAtual).toList()
    val valoresGasto = meses.map { mes -> gastoPorMes[mes] ?: 0f }
    val etiquetasMeses = meses.map { mes -> "M$mes" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Dinheiro gasto",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            text = "Vê quanto estás a investir nos cigarros e ajusta os valores conforme o teu maço.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = precoTexto.value,
                    onValueChange = { novo ->
                        precoTexto.value = novo
                        viewModel.precoMaco = novo.toDoubleOrNull() ?: 0.0
                    },
                    label = { Text("Preço do maço (€)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cigarrosTexto.value,
                    onValueChange = { novo ->
                        cigarrosTexto.value = novo
                        viewModel.cigarrosPorMaco = novo.toIntOrNull() ?: 0
                    },
                    label = { Text("Cigarros por maço") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(2.dp),
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
                    text = "Gasto por mes (historico)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SimpleBarChart(
                    values = valoresGasto,
                    labelFormatter = { value -> String.format("%.2f", value) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                ChartLabelsRow(
                    labels = etiquetasMeses
                )
                Text(
                    text = "Valores reais por mes com base no historico gravado.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Total de cigarros: ${viewModel.cigarrosTotal}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = String.format("Dinheiro gasto: € %.2f", viewModel.dinheiroGasto),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = "Cada cigarro que não fumas é dinheiro que fica contigo (e saúde também).",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}
