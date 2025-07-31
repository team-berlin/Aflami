package usecase.movie

import repository.MovieDetailsRepository

class GetMovieGalleryUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<String> =
        repository.getMovieGallery(movieId = id).take(10)
}