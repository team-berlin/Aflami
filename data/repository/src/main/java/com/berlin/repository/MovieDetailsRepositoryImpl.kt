package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.SearchRemoteDataSource
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(id: Long): Movie? {
        TODO("Not yet implemented")
    }
}