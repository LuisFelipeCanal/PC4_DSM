package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.FlightSearchDatabase

class FlightSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializar la base de datos prepopulada
        FlightSearchDatabase.getDatabase(this)
    }
}

