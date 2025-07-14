package com.berlin.repository.datasource.remote

interface SeriesDetailsRemoteDataSource {
    suspend fun getSeriesImages(id: Long): List<String>
}