package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.ContinueWatchingMovieEntity
import com.berlin.repository.datasource.local.dto.ContinueWatchingTVShowEntity

@Dao
interface ContinueWatchingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingMovie(movieEntity: ContinueWatchingMovieEntity)

    @Query("SELECT * FROM Movie_Continue_Watching ")
    suspend fun getContinueWatchingMovies(): List<ContinueWatchingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContinueWatchingTVShow(tvShowEntity: ContinueWatchingTVShowEntity )

    @Query("SELECT * FROM TVShow_Continue_Watching ")
    suspend fun getContinueWatchingTVShows(): List<ContinueWatchingTVShowEntity>
}