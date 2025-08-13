package com.berlin.repository

import com.berlin.entity.PaginatedResult
import com.berlin.entity.RatedMovie
import com.berlin.entity.RatedTVShow
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toDomainRatedMovie
import com.berlin.repository.mapper.toDomainRatedTVShow
import repository.RatedMediaRepository
import javax.inject.Inject

class RatedMediaRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RatedMediaRepository {

    override suspend fun getRatedMovies(page: Int): PaginatedResult<RatedMovie> {
        val response = remoteDataSource.getRatedMovies(page)
        return PaginatedResult(
            page = response.page ?: 1,
            totalPages = response.totalPages ?: 1,
            results = response.results.orEmpty().map { it.toDomainRatedMovie() }
        )
    }

    override suspend fun getRatedTVShows(page: Int): PaginatedResult<RatedTVShow> {
        val response = remoteDataSource.getRatedTVShows(page)
        return PaginatedResult(
            page = response.page ?: 1,
            totalPages = response.totalPages ?: 1,
            results = response.results.orEmpty().map { it.toDomainRatedTVShow() }
        )
    }
}