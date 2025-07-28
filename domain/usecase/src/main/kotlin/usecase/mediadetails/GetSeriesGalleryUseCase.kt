package usecase.mediadetails

import repository.TVShowDetailsRepository

class GetSeriesGalleryUseCase(private val tvShowDetailsRepository: TVShowDetailsRepository) {
    suspend operator fun invoke(movieId: Long): List<String> =
        tvShowDetailsRepository.getTVShowGallery(tvShowId = movieId)
}