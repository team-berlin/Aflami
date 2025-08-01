package repository

import com.berlin.entity.Episodes
import com.berlin.entity.EpisodesSeason
import com.berlin.entity.Genre
import com.berlin.entity.MediaCast
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails
import com.berlin.entity.Video

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long): TvShowDetails?
    suspend fun getSeriesImages(id: Long): List<String>
    suspend fun getSeriesCastDetails(seriesId: Long): List<MediaCast>
    suspend fun getSeriesSimilar(seriesId: Long): List<TVShow>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episodes?>
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun getTVShowVideos(seriesId: Long): List<Video>

}