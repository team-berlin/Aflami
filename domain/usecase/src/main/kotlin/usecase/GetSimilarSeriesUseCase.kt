package usecase

import com.berlin.entity.TVShow
import repository.TvShowDetailsRepository

class GetSimilarSeriesUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<TVShow> =
        tvShowDetailsRepository.getSeriesSimilar(seriesId)
}