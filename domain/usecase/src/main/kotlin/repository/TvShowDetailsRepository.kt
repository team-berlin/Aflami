package repository

import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.Actor
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails?
    suspend fun getSeriesImages(id: Long): List<String>
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<Actor>
    suspend fun getSeriesSimilar(seriesId: Long): List<TVShow>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episode?>
    suspend fun getSeriesGenres(language: String): List<Genre>
}