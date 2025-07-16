package com.berlin.repository

import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import repository.SeriesDetailsRepository

class TvShowDetailsRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource
) : SeriesDetailsRepository {
    override suspend fun getSeriesDetails(id: Long): TVShow? {
        TODO("Not yet implemented")
    }

}