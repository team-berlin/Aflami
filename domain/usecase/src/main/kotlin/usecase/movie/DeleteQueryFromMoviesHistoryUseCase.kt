package usecase.movie

import repository.MovieRepository

class DeleteQueryFromMoviesHistoryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String) = movieRepository.deleteMovieQueryFromHistory(query)
}