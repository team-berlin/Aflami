package com.berlin.repository

import com.berlin.entity.MediaCast
import com.berlin.entity.TvShowDetails
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import exceptions.AflamiExceptions
import repository.TvShowDetailsRepository

class TvShowDetailsRepositoryImpl(
    private val remoteDataSource: TvShowDetailsRemoteDataSource
) : TvShowDetailsRepository {
    override suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails? {
        return try {
            remoteDataSource.getTvShowDetails(id, language).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }

    override suspend fun getSeriesImages(id: Long): List<String> {
        return try {
            remoteDataSource
                .getSeriesImages(id = id)
                .posters
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast> {
        return remoteDataSource.getSeriesCastDetails(
            seriesId,
            language
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }
}