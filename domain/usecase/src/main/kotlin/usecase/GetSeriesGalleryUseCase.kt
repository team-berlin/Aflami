package usecase

import repository.SeriesDetailsRepository

class GetSeriesGalleryUseCase(private val seriesDetailsRepository: SeriesDetailsRepository) {
    suspend operator fun invoke(movieId: Long): List<String> =
        seriesDetailsRepository.getSeriesImages(id = movieId)
}