package usecase.movie

import repository.MovieRepository
import javax.inject.Inject

class GetRecentMoviesHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<String> = movieRepository.getRecentMoviesSearchQueries()
}