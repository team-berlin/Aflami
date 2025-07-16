package com.berlin.repository

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import repository.SeriesDetailsRepository


class SeriesDetailsRepositoryImpl(
    private val seriesDetailsRemoteDataSource: SeriesDetailsRemoteDataSource,
) : SeriesDetailsRepository {
    override suspend fun getSeriesImages(id: Long): List<String> {
        return try {
            seriesDetailsRemoteDataSource
                .getSeriesImages(id = id)
                .posters
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }

    }
}