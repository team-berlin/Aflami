package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = movieRepository.getPopularMovies()
}