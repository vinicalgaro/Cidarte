package com.vinicalgaro.cidarte.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinicalgaro.cidarte.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_cache WHERE isFavorite = 1 ORDER BY lastUpdated DESC")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies_cache WHERE isInWatchlist = 1 ORDER BY lastUpdated DESC")
    fun getWatchlist(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies_cache WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM movies_cache WHERE id = :id")
    fun observeMovieById(id: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(movie: MovieEntity)

    @Query("DELETE FROM movies_cache WHERE id = :id AND isFavorite = 0 AND isInWatchlist = 0")
    suspend fun deleteIfOrphan(id: Int)
}