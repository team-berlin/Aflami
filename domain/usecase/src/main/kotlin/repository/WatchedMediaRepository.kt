package repository

import com.berlin.entity.Media

interface WatchedMediaRepository {
    suspend fun getWatchedMedia(): List<Media>
    suspend fun insertWatchedMedia(media: Media)
}