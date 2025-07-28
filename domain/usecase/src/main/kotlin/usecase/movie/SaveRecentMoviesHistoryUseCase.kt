package usecase.movie

import repository.MovieRepository

class SaveRecentMoviesHistoryUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String) =
        repository.saveRecentMoviesHistory(query)
}