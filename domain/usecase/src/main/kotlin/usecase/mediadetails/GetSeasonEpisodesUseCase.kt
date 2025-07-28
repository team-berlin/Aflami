package usecase.mediadetails

import com.berlin.entity.Episode
import repository.TVShowDetailsRepository

class GetSeasonEpisodesUseCase(
    private val tvShowDetailsRepository: TVShowDetailsRepository,
) {

    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode?> {
        return tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
    }
}