package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.RoomDatabase

@Database(
    entities = [Airport::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FlightSearchDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate initial airports
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (1,'LAX','Los Angeles International',88068121)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (2,'JFK','John F. Kennedy International',62765044)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (3,'ORD','Chicago O''Hare International',84648760)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (4,'DFW','Dallas/Fort Worth International',75043834)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (5,'ATL','Hartsfield-Jackson Atlanta International',110531300)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (6,'DEN','Denver International',69306167)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (7,'SFO','San Francisco International',58720201)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (8,'LAS','Harry Reid International',51934151)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (9,'SEA','Seattle-Tacoma International',51805690)")
                            db.execSQL("INSERT INTO airport (id, iata_code, name, passengers) VALUES (10,'MIA','Miami International',45936948)")
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

