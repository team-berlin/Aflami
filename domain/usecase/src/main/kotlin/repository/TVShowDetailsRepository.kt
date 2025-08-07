package repository

import com.berlin.entity.Actor
import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.MediaImage
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.Video

interface TVShowDetailsRepository {
    suspend fun getTVShowDetails(id: Long): TVShow
    suspend fun getTVShowsImages(id: Long): MediaImage
    suspend fun getTVShowsCastDetails(seriesId: Long): List<Actor>
    suspend fun getTVShowsSimilar(seriesId: Long): List<TVShow>
    suspend fun getTVShowReviews(id: Long): List<Review>
    suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episode>
    suspend fun getTVShowsGenres(): List<Genre>
    suspend fun getTVShowVideos(seriesId: Long): List<Video>

}