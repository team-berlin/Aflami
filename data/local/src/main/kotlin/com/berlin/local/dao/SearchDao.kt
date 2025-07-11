package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.model.SearchCaching

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_caching WHERE `query` = :query AND `type` = :type")
    suspend fun getCachedSearch(query: String, type: String): List<SearchCaching>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheSearch(searchCaching: List<SearchCaching>)

    @Query("DELETE FROM search_caching WHERE `time` < :expirationTime")
    suspend fun clearExpiredCache(expirationTime: Long)

}