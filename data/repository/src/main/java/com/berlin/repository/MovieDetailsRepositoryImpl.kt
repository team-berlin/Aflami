package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieSimilar(movieId: Long): List<Movie> {
        return movieDetailsRemoteDataSource.getMovieSimilar(movieId).results?.mapNotNull { movieDto ->
            movieDto?.toDomain()
        } ?: emptyList()
    }
}