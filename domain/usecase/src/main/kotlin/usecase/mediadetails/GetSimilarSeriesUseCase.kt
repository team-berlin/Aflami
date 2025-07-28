package usecase.mediadetails

import com.berlin.entity.TVShow
import repository.TVShowDetailsRepository

class GetSimilarSeriesUseCase(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<TVShow> =
        tvShowDetailsRepository.getSimilarTVShows(seriesId)
}