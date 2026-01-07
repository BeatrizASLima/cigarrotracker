package com.example.cigarrotracker.data.service

import com.example.cigarrotracker.data.OverpassResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassApiService {
    @GET("api/interpreter")
    suspend fun search(@Query("data") query: String): OverpassResponse
}
