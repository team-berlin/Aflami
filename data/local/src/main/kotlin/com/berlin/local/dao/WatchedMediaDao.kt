package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.MediaContinueWatchingEntity

@Dao
interface WatchedMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMedia(mediaContinueWatchingEntity: MediaContinueWatchingEntity)

    @Query("SELECT * FROM media_continue_watching ")
    suspend fun getWatchedMedia(): List<MediaContinueWatchingEntity>
}