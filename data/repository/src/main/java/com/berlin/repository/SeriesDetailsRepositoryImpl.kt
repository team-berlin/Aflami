package com.berlin.repository

import android.util.Log
import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import repository.SeriesDetailsRepository


class SeriesDetailsRepositoryImpl(
    private val seriesDetailsRemoteDataSource: SeriesDetailsRemoteDataSource,
) : SeriesDetailsRepository {
    override suspend fun getSeriesImages(id: Long): List<String> =
        seriesDetailsRemoteDataSource.getSeriesImages(id = id).posters?.mapNotNull { it.filePath }
            .also {
                Log.d("Khairy", "getSeriesImages Links are https://image.tmdb.org/t/p/original$it")
            } ?: emptyList()

}