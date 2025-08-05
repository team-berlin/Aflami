package usecase.movie

import repository.MovieRepository

class ClearMoviesSearchHistoryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.clearMovieSearchHistory()
}