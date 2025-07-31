package repository

import com.berlin.entity.Actor
import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.Review
import com.berlin.entity.TVShow

interface TVShowDetailsRepository {
    suspend fun getTVShowDetails(tvShowId: Long): TVShow?
    suspend fun getTVShowGallery(tvShowId: Long): List<String>
    suspend fun getTVShowActors(tvShowId: Long): List<Actor>
    suspend fun getSimilarTVShows(tvShowId: Long): List<TVShow>
    suspend fun getTVShowReviews(tvShowId: Long): List<Review>
    suspend fun getSeasonEpisodes(tvShowId: Long, seasonNumber: Int): List<Episode?>
    suspend fun getTVShowGenres(): List<Genre>
}