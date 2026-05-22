package com.example.pc4_luisfelipecanalalvarado.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pc4_luisfelipecanalalvarado.FlightSearchViewModel
import com.example.pc4_luisfelipecanalalvarado.data.Airport
import com.example.pc4_luisfelipecanalalvarado.data.Favorite

@Composable
fun HomeScreen(
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.searchSuggestions.collectAsState()
    val selectedAirport by viewModel.selectedAirport.collectAsState()
    val flights by viewModel.availableFlights.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Campo de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Buscar aeropuerto...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(Icons.Default.Clear, null)
                    }
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            searchQuery.isBlank() -> {
                // Mostrar favoritos
                FavoritesList(favorites)
            }

            selectedAirport == null -> {
                // Mostrar sugerencias
                SuggestionsList(suggestions) { airport ->
                    viewModel.selectAirport(airport)
                }
            }

            else -> {
                // Mostrar vuelos disponibles
                FlightsList(
                    flights = flights,
                    departureCode = selectedAirport!!.iataCode,
                    favorites = favorites,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private fun SuggestionsList(
    suggestions: List<Airport>,
    onAirportSelected: (Airport) -> Unit
) {
    Text(
        text = "Aeropuertos sugeridos",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    if (suggestions.isEmpty()) {
        Text("Sin resultados", modifier = Modifier.padding(top = 16.dp))
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(suggestions, key = { it.id }) { airport ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAirportSelected(airport) }
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = airport.iataCode, fontWeight = FontWeight.Bold)
                    Text(text = airport.name)
                }
            }
        }
    }
}

@Composable
private fun FlightsList(
    flights: List<Airport>,
    departureCode: String,
    favorites: List<Favorite>,
    viewModel: FlightSearchViewModel
) {
    Text(
        text = "Destinos desde $departureCode",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(flights, key = { it.id }) { flight ->
            val isFavorited = favorites.any {
                it.departureCode == departureCode && it.destinationCode == flight.iataCode
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = flight.iataCode, fontWeight = FontWeight.Bold)
                        Text(text = flight.name)
                    }

                    IconButton(
                        onClick = {
                            if (isFavorited) {
                                viewModel.deleteFavorite(departureCode, flight.iataCode)
                            } else {
                                viewModel.insertFavorite(departureCode, flight.iataCode)
                            }
                        }
                    ) {
                        Text(if (isFavorited) "❤️" else "🤍")
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoritesList(favorites: List<Favorite>) {
    Text(
        text = "Rutas favoritas",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    if (favorites.isEmpty()) {
        Text("Sin rutas favoritas aun", modifier = Modifier.padding(top = 16.dp))
        return
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(favorites, key = { "${it.departureCode}-${it.destinationCode}" }) { favorite ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "${favorite.departureCode} → ${favorite.destinationCode}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

