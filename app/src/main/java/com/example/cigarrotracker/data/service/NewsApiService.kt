package com.example.cigarrotracker.data.service

import com.example.cigarrotracker.data.NoticiaResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Trocar ... pela tua API key do newsapi.org
 */
interface NewsApiService {

    @GET("everything?q=rain&sortBy=popularity&apiKey=COLOCA_SUA_API_KEY_AQUI&pageSize=20")
    suspend fun getNews(@Query("page") page: Int): NoticiaResponse
}
