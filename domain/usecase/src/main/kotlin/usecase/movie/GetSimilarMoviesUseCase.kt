package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository

class GetSimilarMoviesUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Movie> =
        movieDetailsRepository.getSimilarMovies(movieId)
}