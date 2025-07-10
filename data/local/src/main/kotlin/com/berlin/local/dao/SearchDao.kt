package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.local.model.SearchCaching
import com.berlin.local.model.SearchHistory

@Dao
    interface SearchDao {

        @Query("SELECT * FROM search_cache WHERE query = :query")
        suspend fun getCachedSearch(query: String): SearchCaching?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun cacheSearch(entity: SearchCaching)

        @Query("DELETE FROM search_cache WHERE time < :expirationTime")
        suspend fun clearExpiredCache(expirationTime: Long)

        @Query("SELECT * FROM search_history ORDER BY time DESC")
        suspend fun getSearchHistory(): List<SearchHistory>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertSearchHistory(entity: SearchHistory)

        @Query("DELETE FROM search_history")
        suspend fun clearSearchHistory()
    }

