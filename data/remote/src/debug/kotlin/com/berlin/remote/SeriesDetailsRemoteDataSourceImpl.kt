package com.berlin.remote

import com.berlin.repository.datasource.remote.SeriesDetailsRemoteDataSource
import io.ktor.client.HttpClient

class SeriesDetailsRemoteDataSourceImpl(
    private val client: HttpClient,
) : SeriesDetailsRemoteDataSource {
    override suspend fun getSeriesImages(id: Long): List<String> {
        TODO("Not yet implemented")
    }
}