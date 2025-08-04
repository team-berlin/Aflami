package usecase.tvshow

import com.berlin.entity.Actor
import repository.TVShowDetailsRepository

class GetTVShowCastUseCase(
    private val tvShowDetailsRepository: TVShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long): List<Actor> =
        tvShowDetailsRepository.getTVShowsCastDetails(seriesId)
}