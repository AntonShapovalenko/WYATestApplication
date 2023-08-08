package com.example.wyatestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wyatestapp.domain.usecase.PlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val placesUseCase: PlacesUseCase
) : ViewModel() {

    fun getPlaces() = placesUseCase.invoke().stateIn(
        viewModelScope, SharingStarted.Eagerly, Result.success(
            emptyList()
        )
    )
}