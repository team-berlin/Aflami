package repository

import com.berlin.entity.Episode
import com.berlin.entity.TVShow

interface SeriesDetailsRepository {
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episode>
}