package com.example.wyatestapp.domain.usecase

import com.example.wyatestapp.data.Place
import com.example.wyatestapp.domain.repository.PlacesRepository
import javax.inject.Inject

class PlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) : ResultUseCase<Any, Result<List<Place>>>() {

    // This useCase has function that return success result with list of Places from repository
    // or failure result with exception
    override suspend fun doWork(params: Any?): Result<List<Place>> {
        return try {
            Result.success(placesRepository.getPlaces().places)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}