package usecase.mediadetails

import com.berlin.entity.Actor
import repository.TVShowDetailsRepository

class GetSeriesCastUseCase(
    private val seriesDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long, language: String): List<Actor> {
        return seriesDetailsRepository.getTVShowActors(seriesId, language)
    }
}