package com.berlin.repository

import android.util.Log
import com.berlin.repository.datasource.remote.MovieDetailsRemoteDataSource
import repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieDetailsRepository {
    override suspend fun getMovieImages(movieId: Long): List<String> =
        movieDetailsRemoteDataSource.getMovieImages(movieId).posters?.mapNotNull { it.filePath }
            .also {
                Log.d("Khairy", "getMovieImage https://image.tmdb.org/t/p/original$it ")
            }
            ?: emptyList()
}