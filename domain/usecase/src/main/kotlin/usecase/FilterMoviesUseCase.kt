package usecase

import com.berlin.entity.Movie
import repository.SearchRepository

class FilterMoviesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        query: String,
        minRating: Float,
        genres: List<Int>
    ): List<Movie> {
        val movies = searchRepository.searchMovie(query)
        return movies.filter { movie ->
            val meetsRatingCriteria = movie.rating >= minRating
            val meetsGenreCriteria = if (genres.isEmpty() || genres.contains(1)) {
                true
            } else {
                movie.genre.any { genre -> genres.contains(genre) }
            }
            meetsRatingCriteria && meetsGenreCriteria
        }
    }
}