package usecase

import repository.TvShowDetailsRepository

class GetSeriesGalleryUseCase(private val tvShowDetailsRepository: TvShowDetailsRepository) {
    suspend operator fun invoke(movieId: Long): List<String> =
        tvShowDetailsRepository.getSeriesImages(id = movieId)
}