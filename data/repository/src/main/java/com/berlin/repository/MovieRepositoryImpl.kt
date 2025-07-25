package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override suspend fun getUpComingMovies(): List<Movie> {
        return remoteDataSource.getUpComingMovies().results?.map {
            it!!.toDomain()
        } ?: emptyList()
    }
}