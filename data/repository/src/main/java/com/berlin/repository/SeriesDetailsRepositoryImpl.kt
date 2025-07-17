package com.berlin.repository

import com.berlin.entity.Episode
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.mapper.toEpisode
import repository.SeriesDetailsRepository

class SeriesDetailsRepositoryImpl(
    private val remoteDataSource: SeriesDetailsRemoteDataSource
) : SeriesDetailsRepository {

    override suspend fun getSeasonEpisodes(seriesId: Long, seasonNumber: Int): List<Episode> {
        return remoteDataSource.getEpisodeSeasonSeries(seriesId, seasonNumber)
            .results?.filterNotNull()?.map { episodeDto ->
            episodeDto.toEpisode()
        } ?: emptyList()
    }
}