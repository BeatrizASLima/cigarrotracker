package com.example.cigarrotracker.di

import com.example.cigarrotracker.constants.Constants.Companion.NOMINATIM_BASE_URL
import com.example.cigarrotracker.constants.Constants.Companion.OVERPASS_BASE_URL
import com.example.cigarrotracker.data.service.NominatimApiService
import com.example.cigarrotracker.data.service.OverpassApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideOverpassService(): OverpassApiService =
        Retrofit.Builder()
            .baseUrl(OVERPASS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OverpassApiService::class.java)

    @Singleton
    @Provides
    fun provideNominatimService(): NominatimApiService =
        Retrofit.Builder()
            .baseUrl(NOMINATIM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NominatimApiService::class.java)
}
