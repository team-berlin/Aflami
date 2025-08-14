package usecase.movie

import repository.MovieRepository
import javax.inject.Inject

class SaveRecentMoviesHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String) =
        movieRepository.saveRecentMoviesHistory(query)
}