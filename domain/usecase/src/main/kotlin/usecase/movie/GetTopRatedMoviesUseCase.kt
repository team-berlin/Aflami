package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class GetTopRatedMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> = repository.getTopRatedMovies(page)
}