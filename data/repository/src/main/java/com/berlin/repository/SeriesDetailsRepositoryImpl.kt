package com.berlin.repository

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.mapper.toTVShow
import repository.SeriesDetailsRepository

class SeriesDetailsRepositoryImpl(
    private val seriesDetailsRemoteDataSource: SeriesDetailsRemoteDataSource
) : SeriesDetailsRepository {
    override suspend fun getSeriesSimilar(seriesId: Long): List<TVShow> {
        return seriesDetailsRemoteDataSource.getSeriesSimilar(seriesId).results?.mapNotNull { tvShowDto ->
            tvShowDto?.toTVShow()
        } ?: emptyList()
    }
}