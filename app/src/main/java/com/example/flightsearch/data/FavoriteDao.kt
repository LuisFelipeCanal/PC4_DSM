package com.example.flightsearch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("""
        DELETE FROM favorite
        WHERE departure_code = :departureCode
        AND destination_code = :destinationCode
        """)
    suspend fun deleteFavorite(departureCode: String, destinationCode: String)

    @Query("""
        SELECT COUNT(*) FROM favorite
        WHERE departure_code = :departureCode
        AND destination_code = :destinationCode
        """)
    fun isFavorite(departureCode: String, destinationCode: String): Flow<Int>
}

