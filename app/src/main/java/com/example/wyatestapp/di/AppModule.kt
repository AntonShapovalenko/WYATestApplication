package com.example.wyatestapp.di

import com.example.wyatestapp.domain.PlacesApi
import com.example.wyatestapp.domain.repository.PlacesRepository
import com.example.wyatestapp.domain.repository.PlacesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//AppModule that provides repository
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRepository(): PlacesRepository = PlacesRepositoryImpl(provideApi())

    private fun provideApi(): PlacesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PlacesApi.BASE_URL)
            .build()
            .create(PlacesApi::class.java)
    }
}