package usecase.movie

import repository.MovieDetailsRepository

class GetMovieDetailsUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long) =
        repository.getMovieDetails(id)
}