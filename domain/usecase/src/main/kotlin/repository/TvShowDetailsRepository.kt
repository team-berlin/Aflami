package repository

import com.berlin.entity.TvShowDetails

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails?
    suspend fun getSeriesImages(id:Long):List<String>
}