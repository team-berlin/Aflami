package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM TV_SHOW_GENRE")
    suspend fun getCachedTVGenres(): List<TVShowGenreEntity>

    @Query("SELECT * FROM MOVIE_GENRE")
    suspend fun getCachedMovieGenres(): List<MoviesGenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheTVGenres(genres: List<TVShowGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheMovieGenres(genres: List<MoviesGenreEntity>)

    @Query("DELETE FROM TV_SHOW_GENRE")
    suspend fun clearCachedTVGenres()

    @Query("DELETE FROM MOVIE_GENRE")
    suspend fun clearCachedMovieGenres()
}