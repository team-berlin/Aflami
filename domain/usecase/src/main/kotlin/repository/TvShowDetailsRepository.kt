package repository

import com.berlin.entity.MediaCast
import com.berlin.entity.TvShowDetails

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails?
    suspend fun getSeriesImages(id:Long):List<String>
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast>

}