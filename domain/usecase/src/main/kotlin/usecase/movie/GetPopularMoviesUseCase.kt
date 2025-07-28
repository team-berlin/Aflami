package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getPopularMovies()
}