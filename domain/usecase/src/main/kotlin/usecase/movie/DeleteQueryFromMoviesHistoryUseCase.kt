package usecase.movie

import repository.MovieRepository

class DeleteQueryFromMoviesHistoryUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String) = repository.deleteMovieQueryFromHistory(query)
}