package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TvShowDetailsRepository

class GetSimilarTVShowsUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(tvShowId: Long): List<TVShow> =
        tvShowDetailsRepository.getSeriesSimilar(tvShowId)
}