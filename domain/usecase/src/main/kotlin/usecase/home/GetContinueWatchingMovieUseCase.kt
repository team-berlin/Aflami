package usecase.home

import com.berlin.entity.Movie
import repository.ContinueWatchingRepository

class GetContinueWatchingMovieUseCase(
    private val repository: ContinueWatchingRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getContinueWatchingMovies()
    }
}