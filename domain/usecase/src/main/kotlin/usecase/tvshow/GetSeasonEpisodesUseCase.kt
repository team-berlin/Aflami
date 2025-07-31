package usecase.tvshow

import com.berlin.entity.Episode
import repository.TVShowDetailsRepository

class GetSeasonEpisodesUseCase(
    private val repository: TVShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode?> =
        repository.getSeasonEpisodes(seriesId, seasonNumber)
}