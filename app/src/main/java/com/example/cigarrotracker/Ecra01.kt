package com.example.cigarrotracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ecra01(viewModel: CigarroViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "ECRÃ HOJE - CIGARROS", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Cigarros fumados hoje: ${viewModel.cigarrosHoje}")
        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { viewModel.removerCigarro() }) {
                Text("- 1")
            }
            Button(onClick = { viewModel.adicionarCigarro() }) {
                Text("+ 1")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.fecharDia() }) {
            Text("Fechar o dia")
        }
    }
}
