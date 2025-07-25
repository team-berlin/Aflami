package usecase.mediadetails

import com.berlin.entity.Episodes
import repository.TvShowDetailsRepository

class GetSeasonEpisodesUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository,
) {

    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episodes?> {
        return tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
    }
}