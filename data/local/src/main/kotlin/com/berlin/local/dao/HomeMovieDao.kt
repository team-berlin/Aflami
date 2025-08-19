package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection

@Dao
interface HomeMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<HomeMovieEntity>)

    @Query("SELECT * FROM HOME_MOVIE WHERE homeSection = :homeSection")
    suspend fun getMoviesBySection(homeSection: HomeSection): List<HomeMovieEntity>

    @Query("DELETE FROM HOME_MOVIE WHERE homeSection = :homeSection")
    suspend fun clearHomeScreenMovies(homeSection: HomeSection)

    @Query("DELETE FROM HOME_MOVIE")
    suspend fun clearAllMovies()

    @Query(
        """
    SELECT * 
    FROM HOME_MOVIE 
    WHERE homeSection = :home 
      AND CAST(substr(genre, 1, instr(genre || ',', ',') - 1) AS INTEGER) = :genreId
"""
    )
    fun getUpcomingMoviesByGenre(home: HomeSection, genreId: Long?): List<HomeMovieEntity>

}