package usecase

import com.berlin.entity.MediaCast
import repository.SeriesDetailsRepository

class GetSeriesCastUseCase(
    private val seriesDetailsRepository: SeriesDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long, language: String): List<MediaCast> {
        return seriesDetailsRepository.getSeriesCastDetails(seriesId, language)
    }
}