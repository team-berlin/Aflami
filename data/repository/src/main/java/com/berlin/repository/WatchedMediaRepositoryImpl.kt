package com.berlin.repository

import com.berlin.entity.Media
import com.berlin.repository.datasource.local.WatchedMediaLocalDataSource
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toMedia
import repository.WatchedMediaRepository

class WatchedMediaRepositoryImpl (
    private val localDataSource: WatchedMediaLocalDataSource,

): WatchedMediaRepository {
    override suspend fun getWatchedMedia(): List<Media> {
        try {
            return localDataSource.getWatchedMedia().map {
                it.toMedia()
            }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun insertWatchedMedia(media: Media) {
         localDataSource.insertWatchedMedia(media.toLocalEntity())
    }


}