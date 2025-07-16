package usecase

import com.berlin.entity.TVShow
import repository.SeriesDetailsRepository

class GetSimilarSeriesUseCase(
    private val seriesDetailsRepository: SeriesDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<TVShow> =
        seriesDetailsRepository.getSeriesSimilar(seriesId)
}