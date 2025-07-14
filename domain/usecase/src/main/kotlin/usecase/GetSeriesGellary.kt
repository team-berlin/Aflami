package usecase

import repository.SeriesDetailsRepository

class GetSeriesGallery(private val seriesDetailsRepository: SeriesDetailsRepository) {
    suspend operator fun invoke(id: Long): List<String> =
        seriesDetailsRepository.getSeriesImages(id = id)
}