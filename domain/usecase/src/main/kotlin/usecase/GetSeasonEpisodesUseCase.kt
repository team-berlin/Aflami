package usecase

import com.berlin.entity.EpisodesSeason
import jdk.internal.net.http.common.Log
import repository.TvShowDetailsRepository

class GetSeasonEpisodesUseCase (
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {

    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<EpisodesSeason>{
        return tvShowDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
    }
}