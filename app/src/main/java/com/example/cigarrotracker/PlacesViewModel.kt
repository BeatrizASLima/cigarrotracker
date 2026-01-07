package com.example.cigarrotracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cigarrotracker.data.Place
import com.example.cigarrotracker.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlacesUiState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val error: String? = null,
    val lastLat: Double? = null,
    val lastLon: Double? = null
)

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacesUiState())
    val uiState: StateFlow<PlacesUiState> = _uiState

    fun search(lat: Double, lon: Double, radius: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val places = repository.searchPlaces(lat, lon, radius)
                _uiState.value = PlacesUiState(isLoading = false, places = places, lastLat = lat, lastLon = lon)
            } catch (e: Exception) {
                _uiState.value = PlacesUiState(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar"
                )
            }
        }
    }

    fun searchByCity(city: String, radius: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val coords = repository.geocodeCity(city)
                if (coords == null) {
                    _uiState.value = PlacesUiState(isLoading = false, error = "Cidade nao encontrada")
                    return@launch
                }
                val (lat, lon) = coords
                val places = repository.searchPlaces(lat, lon, radius)
                _uiState.value = PlacesUiState(isLoading = false, places = places, lastLat = lat, lastLon = lon)
            } catch (e: Exception) {
                _uiState.value = PlacesUiState(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar"
                )
            }
        }
    }
}
