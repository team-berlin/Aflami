package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.SearchingEntity

@Dao
interface RecentHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueryOnly(searchingEntity: SearchingEntity)

    @Query("DELETE FROM search_cache WHERE `query` = :query")
    suspend fun deleteQueryFromHistory(query: String)

    @Query("DELETE FROM search_cache")
    suspend fun clearSearchHistory()
    
    @Query("SELECT DISTINCT `query` FROM search_cache ORDER BY timeStamp DESC LIMIT 10")
    suspend fun getRecentSearchQueries(): List<String>
}