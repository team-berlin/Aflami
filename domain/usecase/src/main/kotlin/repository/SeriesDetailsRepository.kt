package repository

import com.berlin.entity.TVShow

interface SeriesDetailsRepository {
    suspend fun getSeriesDetails(id: Long): TVShow?
}