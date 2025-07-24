package usecase.mediadetails

import repository.MovieDetailsRepository

class GetMovieGalleryUseCase(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend operator fun invoke(id: Long): List<String> =
        movieDetailsRepository.getMovieImages(movieId = id)
}