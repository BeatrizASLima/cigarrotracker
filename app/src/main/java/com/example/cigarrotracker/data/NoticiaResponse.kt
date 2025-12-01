package com.example.cigarrotracker.data

data class NoticiaResponse(
    val articles: List<Noticia>,
    val status: String,
    val totalResults: Int
)
