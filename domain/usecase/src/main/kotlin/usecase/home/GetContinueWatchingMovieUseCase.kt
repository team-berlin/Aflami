package usecase.home

import com.berlin.entity.Movie
import repository.ContinueWatchingRepository

class GetContinueWatchingMovieUseCase(
    private val repository: ContinueWatchingRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> {
        return repository.getContinueWatchingMovies(page)
    }
}