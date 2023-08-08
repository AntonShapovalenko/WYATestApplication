package com.example.wyatestapp.domain.repository

import com.example.wyatestapp.data.Places

interface PlacesRepository {
    suspend fun getPlaces(): Places
}