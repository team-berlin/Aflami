package usecase.movie

import repository.MovieRepository
import javax.inject.Inject

class ClearMoviesSearchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.clearMovieSearchHistory()
}