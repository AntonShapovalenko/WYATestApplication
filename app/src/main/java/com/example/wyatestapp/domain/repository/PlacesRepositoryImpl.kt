package com.example.wyatestapp.domain.repository

import com.example.wyatestapp.data.Places
import com.example.wyatestapp.domain.PlacesApi

class PlacesRepositoryImpl constructor(
    private val placeApi: PlacesApi
) : PlacesRepository {

    // This is implementation of the interface
    // that in our case return object Place from rest api

    override suspend fun getPlaces(): Places {
        return placeApi.getPlaces()
    }
}