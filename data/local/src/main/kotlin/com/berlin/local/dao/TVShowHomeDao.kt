package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

@Dao
interface TVShowHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShows(tvShows: List<TVShowHomeEntity>)

    @Query("SELECT * FROM TVShow_Home WHERE sectionHome = :sectionHome")
    suspend fun getTVShowsByType(sectionHome: SectionHome): List<TVShowHomeEntity>

    @Query("DELETE FROM TVShow_Home  WHERE sectionHome = :sectionHome")
    suspend fun clearHomeScreenTVShows(sectionHome: SectionHome)
}