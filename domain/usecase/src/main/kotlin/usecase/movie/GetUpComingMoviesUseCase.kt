package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetUpComingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getUpComingMovies()
}