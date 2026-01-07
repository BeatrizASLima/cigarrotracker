package com.example.cigarrotracker.data.repository

import com.example.cigarrotracker.data.NominatimResult
import com.example.cigarrotracker.data.Place
import com.example.cigarrotracker.data.service.NominatimApiService
import com.example.cigarrotracker.data.service.OverpassApiService
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val api: OverpassApiService,
    private val nominatimApi: NominatimApiService
) {
    suspend fun searchPlaces(lat: Double, lon: Double, radius: Int): List<Place> {
        val query = buildQuery(lat, lon, radius)
        val response = api.search(query)
        return response.elements.mapNotNull { element ->
            val eLat = element.lat
            val eLon = element.lon
            val tags = element.tags ?: emptyMap()
            if (eLat == null || eLon == null) {
                return@mapNotNull null
            }
            val name = tags["name"] ?: "Sem nome"
            val address = buildAddress(tags)
            val category = tags["shop"] ?: tags["amenity"] ?: "outro"
            Place(
                id = element.id,
                name = name,
                lat = eLat,
                lon = eLon,
                address = address,
                category = category
            )
        }.take(50)
    }

    suspend fun geocodeCity(city: String): Pair<Double, Double>? {
        val results: List<NominatimResult> = nominatimApi.geocode(city)
        val first = results.firstOrNull() ?: return null
        val lat = first.lat.toDoubleOrNull() ?: return null
        val lon = first.lon.toDoubleOrNull() ?: return null
        return lat to lon
    }

    private fun buildQuery(lat: Double, lon: Double, radius: Int): String {
        return """
            [out:json];
            (
              node["shop"="tobacco"](around:$radius,$lat,$lon);
              node["amenity"="convenience"](around:$radius,$lat,$lon);
            );
            out;
        """.trimIndent()
    }

    private fun buildAddress(tags: Map<String, String>): String {
        val street = tags["addr:street"]
        val number = tags["addr:housenumber"]
        val city = tags["addr:city"]

        val streetPart = when {
            !street.isNullOrBlank() && !number.isNullOrBlank() -> "$street, $number"
            !street.isNullOrBlank() -> street
            else -> null
        }

        val parts = listOfNotNull(streetPart, city)
        return if (parts.isEmpty()) "Sem endereco" else parts.joinToString(" - ")
    }
}
