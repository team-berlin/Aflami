package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

@Dao
interface MediaHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieHomeEntity>)

    @Query("SELECT * FROM Movie_Home ")
    suspend fun getMovies(): List<MovieHomeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShows(tvShows: List<TVShowHomeEntity>)

    @Query("SELECT * FROM TVShow_Home")
    suspend fun getTVShows(): List<TVShowHomeEntity>

    @Query("DELETE FROM Movie_Home")
    suspend fun clearMovies()

    @Query("DELETE FROM TVShow_Home")
    suspend fun clearTVShows()
}