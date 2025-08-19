package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity

@Dao
interface RecentHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueryOnly(searchingEntity: RecentSearchHistoryEntity)

    @Query("DELETE FROM recent_search_history WHERE `query` = :query")
    suspend fun deleteQueryFromHistory(query: String)

    @Query("DELETE FROM recent_search_history")
    suspend fun clearSearchHistory()
    
    @Query("SELECT DISTINCT `query` FROM recent_search_history ORDER BY time DESC LIMIT 10")
    suspend fun getRecentSearchQueries(): List<String>
}