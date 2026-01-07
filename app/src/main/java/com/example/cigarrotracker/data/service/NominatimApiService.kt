package com.example.cigarrotracker.data.service

import com.example.cigarrotracker.data.NominatimResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NominatimApiService {
    @Headers("User-Agent: CigarroTrackerApp")
    @GET("search")
    suspend fun geocode(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): List<NominatimResult>
}
