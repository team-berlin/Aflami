package repository

import com.berlin.entity.Actor
import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.MediaImage
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.Video

interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(id: Long): TVShow
    suspend fun getSeriesImages(id: Long): MediaImage
    suspend fun getSeriesCastDetails(seriesId: Long): List<Actor>
    suspend fun getSeriesSimilar(seriesId: Long): List<TVShow>
    suspend fun getReviews(id: Long): List<Review>
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episode>
    suspend fun getSeriesGenres(): List<Genre>
    suspend fun getTVShowVideos(seriesId: Long): List<Video>

}