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
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PlacesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: MapsViewModel
    private lateinit var placesUseCase: PlacesUseCase
    private val mockList = listOf(
        Place(
            id = "id",
            name = "name",
            address = "address",
            location = Location(0.0, 0.0)
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        placesUseCase = mockk()
        every { placesUseCase.invoke() } returns flow {
            emit(Result.success(mockList))
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
    fun `check list is not empty`() = runBlocking {
        dispatcher.advance(1000)
        val list = vm.places.value.getOrNull()
        assert(list?.isNotEmpty() ?: false)
    }

    private fun TestDispatcher.advance(millis: Long) {
        scheduler.apply { advanceTimeBy(millis); runCurrent() }
    }
}