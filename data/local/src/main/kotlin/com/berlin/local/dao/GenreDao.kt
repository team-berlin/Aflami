package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre_cache WHERE type = :type")
    suspend fun getCachedGenres(type: String): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genre_cache WHERE type = :type")
    suspend fun clearCachedGenres(type: String)
}