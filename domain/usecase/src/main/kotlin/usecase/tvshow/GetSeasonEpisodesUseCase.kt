package usecase.tvshow

import com.berlin.entity.Episode
import repository.TvShowDetailsRepository

class GetSeasonEpisodesUseCase(
    private val repository: TvShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode> =
        repository.getSeasonEpisodes(seriesId, seasonNumber)
}