package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.SectionHome

@Dao
interface HomeMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieHomeEntity>)

    @Query("SELECT * FROM Movie_Home WHERE sectionHome = :sectionHome")
    suspend fun getMoviesBySection(sectionHome: SectionHome): List<MovieHomeEntity>

    @Query("DELETE FROM Movie_Home WHERE sectionHome = :sectionHome")
    suspend fun clearHomeScreenMovies(sectionHome: SectionHome)

    @Query("DELETE FROM Movie_Home")
    suspend fun clearAllMovies()

}