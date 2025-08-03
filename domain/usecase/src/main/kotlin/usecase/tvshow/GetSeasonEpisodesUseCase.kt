package usecase.tvshow

import com.berlin.entity.Episode
import repository.TvShowDetailsRepository
import javax.inject.Inject

class GetSeasonEpisodesUseCase @Inject constructor(
    private val repository: TvShowDetailsRepository,
) {
    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode> =
        repository.getSeasonEpisodes(seriesId, seasonNumber)
}