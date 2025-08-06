package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.MovieHomeEntity

@Dao
interface MovieHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieHomeEntity>)

    @Query("SELECT * FROM Movie_Home WHERE sectionHome = :sectionHome")
    suspend fun getMoviesByType(sectionHome: SectionHome): List<MovieHomeEntity>

    @Query("DELETE FROM Movie_Home WHERE sectionHome = :sectionHome")
    suspend fun clearHomeScreenMovies(sectionHome: SectionHome)

}