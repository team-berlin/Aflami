package repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface ContinueWatchingRepository {
    suspend fun getContinueWatchingMovies(): List<Movie>
    suspend fun addContinueWatchingMovie(movie: Movie)
    suspend fun getContinueWatchingTVShows(): List<TVShow>
    suspend fun addContinueWatchingTVShow(tvShow: TVShow)
}