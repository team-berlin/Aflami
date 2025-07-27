package usecase.mediadetails

import com.berlin.entity.Episode
import repository.TvShowDetailsRepository

class GetSeasonEpisodesUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository,
) {

    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode?> {
        return tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
    }
}