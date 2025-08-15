package usecase.movie

import repository.MovieRepository
import javax.inject.Inject

class DeleteQueryFromMoviesHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String) = movieRepository.deleteMovieQueryFromHistory(query)
}