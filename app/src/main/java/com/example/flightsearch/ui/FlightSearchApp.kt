package com.example.flightsearch.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.screens.HomeScreen

@Composable
fun FlightSearchApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val viewModel: FlightSearchViewModel = viewModel()
        HomeScreen(viewModel = viewModel)
    }
}

