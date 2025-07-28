package usecase.movie

import repository.MovieRepository

class GetRecentMoviesHistoryUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<String> = repository.getRecentMoviesSearchQueries()
}