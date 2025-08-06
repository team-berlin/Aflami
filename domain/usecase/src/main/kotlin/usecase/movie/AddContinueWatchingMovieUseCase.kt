package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class AddContinueWatchingMovieUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = movieRepository.addContinueWatchingMovie(movie)
}