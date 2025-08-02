package usecase.tvshow

import com.berlin.entity.Actor
import repository.TVShowDetailsRepository
import repository.TvShowDetailsRepository

class GetTVShowCastUseCase(
    private val repository: TvShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<Actor> =
        repository.getTVShowActors(seriesId)
}