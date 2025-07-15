package com.berlin.repository

import android.util.Log
import com.berlin.entity.MediaCast
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import com.berlin.repository.mapper.toDomain
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieCastDetails(movieId: Long, language: String): List<MediaCast> {
        return remoteDataSource.getMovieCastDetails(
            movieId,
            language
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        }.also {
            Log.e("Domain cast response", "$it")
        } ?: emptyList()
    }
}