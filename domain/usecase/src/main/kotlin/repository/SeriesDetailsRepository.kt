package repository

import com.berlin.entity.MediaCast

interface SeriesDetailsRepository {
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast>

}