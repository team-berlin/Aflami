package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository

class ContinueWatchingMovieUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(page: Int): List<Movie> = repository.getContinueWatchingMovies(page)
}