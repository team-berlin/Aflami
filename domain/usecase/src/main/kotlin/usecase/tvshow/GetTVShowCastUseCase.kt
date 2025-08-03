package usecase.tvshow

import com.berlin.entity.Actor
import repository.TvShowDetailsRepository

class GetTVShowCastUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<Actor> =
        tvShowDetailsRepository.getSeriesCastDetails(seriesId)
}