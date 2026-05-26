package com.example.flightsearch;

import android.app.Application;

/**
 * Java fallback Application class to ensure the application class is present in the final APK.
 * Kept minimal — delegates any heavy initialization to existing Kotlin code if present.
 */
public class FlightSearchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Si existe la clase Kotlin/DB, intentar inicializarla (no obliga a que exista)
        try {
            com.example.flightsearch.data.FlightSearchDatabase.getDatabase(this);
        } catch (Throwable ignored) {
            // Ignorar si la DB no está disponible en este punto; sólo intentamos asegurar que
            // la clase Application exista para evitar ClassNotFoundException al arrancar.
        }
    }
}

