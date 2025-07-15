package com.berlin.repository

import com.berlin.entity.MediaCast
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.SeriesDetailsRepository

class SeriesDetailsRepositoryImpl(
    private val remoteDataSource: SeriesDetailsRemoteDataSource
) : SeriesDetailsRepository {
    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast> {
        return remoteDataSource.getSeriesCastDetails(
            seriesId,
            language
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }

}