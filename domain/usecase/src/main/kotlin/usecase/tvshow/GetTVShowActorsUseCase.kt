package usecase.tvshow

import com.berlin.entity.Actor
import repository.TVShowDetailsRepository

class GetTVShowActorsUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<Actor> =
        repository.getTVShowActors(seriesId)
}