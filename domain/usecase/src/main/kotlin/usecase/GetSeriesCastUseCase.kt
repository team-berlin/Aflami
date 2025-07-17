package usecase

import com.berlin.entity.MediaCast
import repository.TvShowDetailsRepository

class GetSeriesCastUseCase(
    private val seriesDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long, language: String): List<MediaCast> {
        return seriesDetailsRepository.getSeriesCastDetails(seriesId, language)
    }
}