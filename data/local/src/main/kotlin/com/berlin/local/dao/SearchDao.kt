package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.RecentSearchHistoryEntity
import com.berlin.repository.util.QueryType

@Dao
interface SearchDao {
    @Query(
        """SELECT * FROM recent_search_history 
        WHERE `query` = :query AND `type` = :type 
        LIMIT :pageSize OFFSET :skip"""
    )
    suspend fun getCachedSearch(
        query: String,
        type: QueryType,
        pageSize: Int,
        skip: Int
    ): List<RecentSearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheSearch(searchCaching: List<RecentSearchHistoryEntity>)


}