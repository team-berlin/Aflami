package com.berlin.repository

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import repository.SeriesDetailsRepository


class SeriesDetailsRepositoryImpl(
    private val seriesDetailsRemoteDataSource: SeriesDetailsRemoteDataSource,
) : SeriesDetailsRepository {
    override suspend fun getSeriesImages(id: Long): List<String> =
        seriesDetailsRemoteDataSource.getSeriesImages(id = id)

}