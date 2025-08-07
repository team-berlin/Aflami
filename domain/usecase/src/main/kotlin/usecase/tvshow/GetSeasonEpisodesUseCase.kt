package usecase.tvshow

import com.berlin.entity.Episode
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetSeasonEpisodesUseCase @Inject constructor(
    private val tvShowRepository: TVShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode> =
        tvShowRepository.getSeasonEpisodes(seriesId, seasonNumber)
}