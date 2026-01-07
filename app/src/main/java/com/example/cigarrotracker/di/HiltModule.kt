package com.example.cigarrotracker.di

import com.example.cigarrotracker.data.repository.PlacesRepository
import com.example.cigarrotracker.data.service.NominatimApiService
import com.example.cigarrotracker.data.service.OverpassApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun providePlacesRepository(
        overpassApiService: OverpassApiService,
        nominatimApiService: NominatimApiService
    ): PlacesRepository = PlacesRepository(overpassApiService, nominatimApiService)
}
