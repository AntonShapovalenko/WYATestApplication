package com.example.wyatestapp

import com.example.wyatestapp.data.Location
import com.example.wyatestapp.data.Place
import com.example.wyatestapp.domain.usecase.PlacesUseCase
import com.example.wyatestapp.presentation.MapsViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PlacesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: MapsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        val placesUseCase: PlacesUseCase = mockk()

        every { placesUseCase.invoke() } returns flow {
            emit(Result.success(emptyList()))
        }

        vm = spyk(
            MapsViewModel(placesUseCase),
            recordPrivateCalls = true,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check list is empty`() = runBlocking {
        val list = vm.getPlaces().value.getOrNull()
        assert(list?.isEmpty() ?: false)
    }
}