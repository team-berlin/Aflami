package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MovieEntity
import com.berlin.repository.datasource.local.model.SearchCaching

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_cache WHERE `query` = :query")
    suspend fun getCachedSearch(query: String, type: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheSearch(searchCaching: List<MovieEntity>)

    @Query("DELETE FROM search_cache WHERE time < :expirationTime")
    suspend fun clearExpiredCache(expirationTime: Long)

}