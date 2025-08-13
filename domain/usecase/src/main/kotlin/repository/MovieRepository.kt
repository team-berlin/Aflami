package repository

import com.berlin.entity.Movie

interface MovieRepository {
    //region Recently Watched Movies
    suspend fun getContinueWatchingMovies(page: Int): List<Movie>
    suspend fun addContinueWatchingMovie(movie: Movie)
    //endregion

    //region home movies
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getUpComingMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getMoviesByMoods(moods: List<Int>): List<Movie>
    //endregion

    //region Search Movies
    suspend fun getMoviesByCountry(query: String, page: Int): List<Movie>
    suspend fun getMoviesByActorName(actorName: String, page: Int): List<Movie>
    suspend fun getMovieByKeyWord(query: String, page: Int): List<Movie>
    //endregion

    //region Recent Search Queries
    suspend fun getRecentMoviesSearchQueries(): List<String>
    suspend fun saveRecentMoviesHistory(query: String)
    suspend fun deleteMovieQueryFromHistory(query: String)
    suspend fun clearMovieSearchHistory()
    suspend fun getMoviesByCategory(genreId: Long, page: Int): List<Movie>
    //endregion

    //regin Game
    suspend fun getMovieGame():List<Movie>
    //endregion

}