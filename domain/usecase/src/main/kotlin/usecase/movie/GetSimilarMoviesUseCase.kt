package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Movie> =
        movieDetailsRepository.getSimilarMovies(movieId)
}