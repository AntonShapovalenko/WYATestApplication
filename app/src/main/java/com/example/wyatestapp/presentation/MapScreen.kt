package com.example.wyatestapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wyatestapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
) {
    val viewModel: MapsViewModel = hiltViewModel()
    val allPlaces by viewModel.getPlaces().collectAsState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val initialZoom = 11f
    val defaultDestinationLatLng = LatLng(51.509865, -0.118092)
    val errorMessage = stringResource(id = R.string.error_message)

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) { padding ->

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultDestinationLatLng, initialZoom)
        }

        LaunchedEffect(key1 = allPlaces) {
            // If we receive failure result from useCase in viewModel
            // display a message with the error text
            if (allPlaces.isFailure) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage,
                )
            }
        }

        LaunchedEffect(key1 = Unit) {
            // Animate our camera to the default location
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        defaultDestinationLatLng, initialZoom, 0f, 0f
                    )
                ),
                durationMs = 1000
            )
        }

        // Adding a map to the screen
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            if (allPlaces.isSuccess) {
                // If successful, we check the list of places and add a marker to the map
                allPlaces.getOrNull()?.forEach { spot ->
                    val markerState =
                        rememberMarkerState(position = LatLng(spot.location.lat, spot.location.lng))
                    Marker(
                        state = markerState,
                        title = spot.name,
                        snippet = spot.address,
                        onClick = {
                            it.showInfoWindow()
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_CYAN
                        )
                    )
                }
            }
        }
    }
}