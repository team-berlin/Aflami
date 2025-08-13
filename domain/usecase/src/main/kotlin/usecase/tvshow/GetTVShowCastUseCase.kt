package usecase.tvshow

import com.berlin.entity.Actor
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowCastUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long): List<Actor> =
        tvShowDetailsRepository.getTVShowsCastDetails(seriesId)
}