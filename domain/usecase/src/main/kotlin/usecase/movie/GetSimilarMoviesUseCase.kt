package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository

class GetSimilarMoviesUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Movie> =
        repository.getSimilarMovies(movieId)
}