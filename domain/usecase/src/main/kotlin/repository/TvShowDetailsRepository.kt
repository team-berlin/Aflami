package repository

import com.berlin.entity.Episodes
import com.berlin.entity.EpisodesSeason
import com.berlin.entity.Genre
import com.berlin.entity.MediaCast
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails?
    suspend fun getSeriesImages(id: Long): List<String>
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast>
    suspend fun getSeriesSimilar(seriesId: Long): List<TVShow>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episodes?>
    suspend fun getTVGenres(language: String): List<Genre>
}