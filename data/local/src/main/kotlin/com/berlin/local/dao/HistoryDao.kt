package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.SearchingHistory
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM Searching_history ORDER BY timestamp DESC")
    fun getAllSearches(): Flow<List<SearchingHistory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearch(search: SearchingHistory)

    @Query("DELETE FROM Searching_history WHERE id = :id")
    suspend fun deleteSearchById(id: Int)

    @Query("DELETE FROM Searching_history")
    suspend fun clearAll()
}
