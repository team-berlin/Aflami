package com.berlin.usecase

import com.berlin.entity.Episode
import repository.SeriesDetailsRepository

class GetSeasonEpisodesUseCase (
    private val seriesDetailsRepository: SeriesDetailsRepository
) {

    suspend operator fun invoke(seriesId: Long, seasonNumber: Int): List<Episode>{
          return seriesDetailsRepository.getSeasonEpisodes(seriesId, seasonNumber)
    }
}