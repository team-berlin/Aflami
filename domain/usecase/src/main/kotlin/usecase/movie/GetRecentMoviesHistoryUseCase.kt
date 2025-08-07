package usecase.movie

import repository.MovieRepository

class GetRecentMoviesHistoryUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<String> = movieRepository.getRecentMoviesSearchQueries()
}