package com.example.flightsearch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.FlightSearchViewModel

@Composable
fun HomeScreen(viewModel: FlightSearchViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.searchSuggestions.collectAsState()
    val selectedAirport by viewModel.selectedAirport.collectAsState()
    val flights by viewModel.availableFlights.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Campo de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Buscar aeropuerto...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(Icons.Default.Clear, null)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            // Mostrar sugerencias de autocompletar
            searchQuery.isNotBlank() && selectedAirport == null -> {
                SuggestionsList(suggestions) { airport ->
                    viewModel.selectAirport(airport)
                }
            }
            // Mostrar lista de vuelos disponibles
            selectedAirport != null -> {
                FlightList(selectedAirport!!, flights, favorites, viewModel)
            }
            // Mostrar favoritos cuando no hay búsqueda
            else -> {
                FavoritesList(favorites, viewModel)
            }
        }
    }
}

@Composable
fun SuggestionsList(suggestions: List<Airport>, onAirportSelected: (Airport) -> Unit) {
    LazyColumn {
        items(suggestions, key = { it.id ?: it.iataCode }) { airport ->
            SuggestionItem(airport) { onAirportSelected(airport) }
        }
    }
}

@Composable
fun SuggestionItem(airport: Airport, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFECEFF1)
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = Color(0xFFBBBBBB)),
                    onClick = onClick
                )
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = airport.iataCode, style = MaterialTheme.typography.bodyLarge, color = Color(0xFF212121))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = airport.name, style = MaterialTheme.typography.bodySmall, color = Color(0xFF424242))
            }
        }
    }
}

@Composable
fun FlightList(
    departure: Airport,
    flights: List<Airport>,
    favorites: List<com.example.flightsearch.data.Favorite>,
    viewModel: FlightSearchViewModel
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Vuelos desde: ${departure.iataCode}", style = MaterialTheme.typography.titleMedium)
            Button(onClick = { viewModel.clearSelection() }) {
                Text("Volver")
            }
        }

        LazyColumn {
            items(flights) { destination ->
                FlightItem(departure, destination, favorites, viewModel)
            }
        }
    }
}

@Composable
fun FlightItem(
    departure: Airport,
    destination: Airport,
    favorites: List<com.example.flightsearch.data.Favorite>,
    viewModel: FlightSearchViewModel
) {
    val isFav = favorites.any {
        it.departureCode == departure.iataCode && it.destinationCode == destination.iataCode
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("${departure.iataCode} → ${destination.iataCode}", style = MaterialTheme.typography.bodyLarge)
                Text(destination.name, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = {
                if (isFav) {
                    viewModel.removeFavorite(departure.iataCode, destination.iataCode)
                } else {
                    viewModel.addFavorite(departure.iataCode, destination.iataCode)
                }
            }) {
                Text(if (isFav) "★" else "☆", fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            }
        }
        HorizontalDivider()
    }
}

@Composable
fun FavoritesList(favorites: List<com.example.flightsearch.data.Favorite>, viewModel: FlightSearchViewModel) {
    if (favorites.isEmpty()) {
        Text("No hay favoritos", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(favorites) { favorite ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${favorite.departureCode} → ${favorite.destinationCode}")
                        IconButton(onClick = {
                            viewModel.removeFavorite(favorite.departureCode, favorite.destinationCode)
                        }) {
                            Text("★", fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
