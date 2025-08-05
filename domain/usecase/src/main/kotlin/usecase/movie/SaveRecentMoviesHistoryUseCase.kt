package usecase.movie

import repository.MovieRepository

class SaveRecentMoviesHistoryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String) =
        movieRepository.saveRecentMoviesHistory(query)
}