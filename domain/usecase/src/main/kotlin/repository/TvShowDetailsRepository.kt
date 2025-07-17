package repository

import com.berlin.entity.TvShowDetails

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails?
}