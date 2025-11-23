package com.example.cigarrotracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ecra03(viewModel: CigarroViewModel) {
    val precoTexto = remember { mutableStateOf(viewModel.precoMaco.toString()) }
    val cigarrosTexto = remember { mutableStateOf(viewModel.cigarrosPorMaco.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("DINHEIRO GASTO", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(24.dp))

        Text("Total de cigarros: ${viewModel.cigarrosTotal}")
        Text(String.format("Dinheiro gasto: € %.2f", viewModel.dinheiroGasto))
    }
}
