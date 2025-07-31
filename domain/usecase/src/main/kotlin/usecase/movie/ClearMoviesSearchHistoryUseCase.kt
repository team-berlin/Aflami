package usecase.movie

import repository.MovieRepository

class ClearMoviesSearchHistoryUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.clearMovieSearchHistory()
}