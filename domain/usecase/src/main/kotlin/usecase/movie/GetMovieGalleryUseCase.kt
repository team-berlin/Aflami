package usecase.movie

import repository.MovieDetailsRepository

class GetMovieGalleryUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<String> {
        //movieDetailsRepository.getMovieImages(id)
        return emptyList()
    }
}