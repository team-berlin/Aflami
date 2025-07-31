package repository

import com.berlin.entity.Movie

interface MovieRepository {
    suspend fun getContinueWatchingMovies(page: Int): List<Movie>
    suspend fun addContinueWatchingMovie(movie: Movie)
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getUpComingMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMoviesByCountry(query: String, page: Int): List<Movie>
    suspend fun getMediaByActorName(actorName: String, page: Int): List<Movie>
    suspend fun searchMovie(query: String, page: Int): List<Movie>
    suspend fun getRecentMoviesSearchQueries(): List<String>
    suspend fun saveRecentMoviesHistory(query: String)
    suspend fun deleteMovieQueryFromHistory(query: String)
    suspend fun clearMovieSearchHistory()
}