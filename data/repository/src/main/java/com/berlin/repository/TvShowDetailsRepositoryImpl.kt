package com.berlin.repository

import com.berlin.entity.TvShowDetails
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
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
}