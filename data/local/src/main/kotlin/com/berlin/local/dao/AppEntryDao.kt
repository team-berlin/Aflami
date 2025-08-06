package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.AppEntryEntity

@Dao
interface AppEntryDao {

    @Query("SELECT * FROM app_entry LIMIT 1")
    suspend fun getAppEntry(): AppEntryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppEntry(appEntry: AppEntryEntity)
}