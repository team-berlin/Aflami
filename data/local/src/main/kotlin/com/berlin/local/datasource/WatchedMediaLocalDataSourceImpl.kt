package com.berlin.local.datasource

import com.berlin.local.dao.WatchedMediaDao
import com.berlin.repository.datasource.local.WatchedMediaLocalDataSource
import com.berlin.repository.datasource.local.dto.MediaContinueWatchingEntity

class WatchedMediaLocalDataSourceImpl(
    private val mediaWatchedDao: WatchedMediaDao
) : WatchedMediaLocalDataSource {
    override suspend fun getWatchedMedia(): List<MediaContinueWatchingEntity> {
        return mediaWatchedDao.getWatchedMedia()
    }

    override suspend fun insertWatchedMedia(media:MediaContinueWatchingEntity) {
        return mediaWatchedDao.insertWatchedMedia(media)
    }

}