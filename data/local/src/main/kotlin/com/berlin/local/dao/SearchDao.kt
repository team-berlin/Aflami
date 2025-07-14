package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.SearchingEntity

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_cache WHERE `query` = :query And `type` = :type")
    suspend fun getCachedSearch(query: String, type: String): List<SearchingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheSearch(searchCaching: List<SearchingEntity>)

    @Query("SELECT DISTINCT `query` FROM search_cache ORDER BY time DESC LIMIT 10")
    suspend fun getRecentSearchQueries(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQueryOnly(searchingEntity: SearchingEntity)

    @Query("DELETE FROM search_cache WHERE `query` = :query")
    suspend fun deleteQueryFromHistory(query: String)

    @Query("DELETE FROM search_cache")
    suspend fun clearSearchHistory()


}