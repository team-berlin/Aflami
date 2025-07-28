package usecase.mediadetails

import repository.MovieDetailsRepository

class GetMovieDetailsUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long, language: String) = repository.getMovieDetails(id, language)
}