package usecase.mediadetails

import com.berlin.entity.Actor
import repository.TvShowDetailsRepository

class GetSeriesCastUseCase(
    private val seriesDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long, language: String): List<Actor> {
        return seriesDetailsRepository.getSeriesCastDetails(seriesId, language)
    }
}