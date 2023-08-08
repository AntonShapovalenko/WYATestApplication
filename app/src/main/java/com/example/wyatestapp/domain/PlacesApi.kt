package com.example.wyatestapp.domain
import com.example.wyatestapp.data.Places
import retrofit2.http.GET

interface PlacesApi {

        @GET("/api/places")
        suspend fun getPlaces(): Places

        companion object {
            const val BASE_URL = "https://test.wya.world"
        }

}