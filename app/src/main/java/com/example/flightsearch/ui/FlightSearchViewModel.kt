package com.example.flightsearch.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("flight_search_prefs")

class FlightSearchViewModel(application: Application) :
    AndroidViewModel(application) {

    private val SEARCH_QUERY_KEY = stringPreferencesKey("search_query")
    private val db = FlightSearchDatabase.getDatabase(application)
    private val airportDao = db.airportDao()
    private val favoriteDao = db.favoriteDao()
    private val dataStore = application.dataStore

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedAirport = MutableStateFlow<Airport?>(null)
    val selectedAirport: StateFlow<Airport?> = _selectedAirport.asStateFlow()

    val searchSuggestions: StateFlow<List<Airport>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) flowOf(emptyList())
            else airportDao.searchAirports(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableFlights: StateFlow<List<Airport>> = _selectedAirport
        .flatMapLatest { airport ->
            if (airport == null) flowOf(emptyList())
            else airportDao.getAllExcept(airport.iataCode)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<Favorite>> = favoriteDao.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadSearchQuery()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[SEARCH_QUERY_KEY] = query
            }
        }
    }

    fun selectAirport(airport: Airport) {
        _selectedAirport.value = airport
    }

    fun clearSelection() {
        _selectedAirport.value = null
        updateSearchQuery("")
    }

    fun addFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            favoriteDao.insertFavorite(
                Favorite(
                    departureCode = departureCode,
                    destinationCode = destinationCode
                )
            )
        }
    }

    fun removeFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            favoriteDao.deleteFavorite(departureCode, destinationCode)
        }
    }

    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean> {
        return favoriteDao.isFavorite(departureCode, destinationCode)
            .map { it > 0 }
    }

    private fun loadSearchQuery() {
        viewModelScope.launch {
            dataStore.data
                .map { preferences ->
                    preferences[SEARCH_QUERY_KEY] ?: ""
                }
                .collect { query ->
                    _searchQuery.value = query
                }
        }
    }
}


