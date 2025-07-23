package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.MovieRemoteDataSource
import com.berlin.repository.mapper.toMovie
import repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getUpComingMovies(): List<Movie> {
        return remoteDataSource.getUpComingMovies().results?.map { it!!.toMovie() } ?: emptyList()
    }
}