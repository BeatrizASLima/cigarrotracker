package com.example.cigarrotracker.data

data class Place(
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val category: String
)
