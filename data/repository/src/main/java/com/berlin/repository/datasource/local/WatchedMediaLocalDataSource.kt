package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MediaContinueWatchingEntity

interface WatchedMediaLocalDataSource {
    suspend fun getWatchedMedia(): List<MediaContinueWatchingEntity>

    suspend fun insertWatchedMedia(media:MediaContinueWatchingEntity)

}