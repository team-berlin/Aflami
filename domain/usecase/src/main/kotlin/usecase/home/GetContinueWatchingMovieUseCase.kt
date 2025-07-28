package usecase.home

import com.berlin.entity.Movie
import repository.MovieRepository

class GetContinueWatchingMovieUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getContinueWatchingMovies()
    }
}