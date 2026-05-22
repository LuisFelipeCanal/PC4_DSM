package com.example.pc4_luisfelipecanalalvarado.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "airport",
    indices = [Index(value = ["iata_code"], unique = true)]
)
data class AirportEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @ColumnInfo(name = "name")
    val name: String
)

