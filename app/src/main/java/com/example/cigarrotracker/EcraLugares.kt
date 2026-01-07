package com.example.cigarrotracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun EcraLugares() {
    val viewModel = hiltViewModel<PlacesViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    var latText by remember { mutableStateOf("38.72") }
    var lonText by remember { mutableStateOf("-9.13") }
    var radiusText by remember { mutableStateOf("1500") }
    var cityText by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState.lastLat, uiState.lastLon) {
        val lat = uiState.lastLat
        val lon = uiState.lastLon
        if (lat != null && lon != null) {
            latText = lat.toString()
            lonText = lon.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Lugares para comprar tabaco",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Use latitude, longitude e raio em metros.",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = latText,
            onValueChange = { latText = it },
            label = { Text("Latitude") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lonText,
            onValueChange = { lonText = it },
            label = { Text("Longitude") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = radiusText,
            onValueChange = { radiusText = it },
            label = { Text("Raio (metros)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cityText,
            onValueChange = { cityText = it },
            label = { Text("Cidade") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val lat = latText.toDoubleOrNull()
                val lon = lonText.toDoubleOrNull()
                val radius = radiusText.toIntOrNull()
                inputError = if (lat == null || lon == null || radius == null || radius <= 0) {
                    "Preencha latitude, longitude e raio validos."
                } else {
                    viewModel.search(lat, lon, radius)
                    null
                }
            }
        ) {
            Text("Buscar por coordenadas")
        }

        Button(
            onClick = {
                val radius = radiusText.toIntOrNull()
                inputError = if (cityText.isBlank() || radius == null || radius <= 0) {
                    "Preencha cidade e raio validos."
                } else {
                    viewModel.searchByCity(cityText.trim(), radius)
                    null
                }
            }
        ) {
            Text("Buscar por cidade")
        }

        if (inputError != null) {
            Text(text = inputError.orEmpty(), color = MaterialTheme.colorScheme.error)
        }

        if (uiState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.error != null) {
            Text(text = "Erro: ${uiState.error}", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.places) { place ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = place.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = place.address, style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Categoria: ${place.category}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
