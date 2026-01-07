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
    val gastoPorDia = if (viewModel.cigarrosPorMaco == 0) {
        0f
    } else {
        (viewModel.mediaPorDia.toFloat() / viewModel.cigarrosPorMaco) * viewModel.precoMaco.toFloat()
    }
    val gastoSemanal = gastoPorDia * 7f
    val valoresSemanas = (1..4).map { semana -> gastoSemanal * semana }
    val etiquetasSemanas = listOf("S1", "S2", "S3", "S4")

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
                    text = "Gasto acumulado (projecao)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SimpleBarChart(
                    values = valoresSemanas,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                ChartLabelsRow(
                    labels = etiquetasSemanas
                )
                Text(
                    text = "Valores acumulados por semana com base na media atual.",
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
