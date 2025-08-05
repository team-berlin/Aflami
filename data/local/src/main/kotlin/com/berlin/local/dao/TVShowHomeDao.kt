package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MediaType
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

@Dao
interface TVShowHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShows(tvShows: List<TVShowHomeEntity>)

    @Query("SELECT * FROM TVShow_Home WHERE type = :type")
    suspend fun getTVShowsByType(type: MediaType): List<TVShowHomeEntity>

    @Query("DELETE FROM TVShow_Home")
    suspend fun clearTVShows()
}