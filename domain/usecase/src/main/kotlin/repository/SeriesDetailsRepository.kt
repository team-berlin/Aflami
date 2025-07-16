package repository

import com.berlin.entity.TVShow

interface SeriesDetailsRepository {
    suspend fun getSeriesSimilar(seriesId:Long):List<TVShow>
}