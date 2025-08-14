package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository
import javax.inject.Inject

class AddContinueWatchingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = movieRepository.addContinueWatchingMovie(movie)
}