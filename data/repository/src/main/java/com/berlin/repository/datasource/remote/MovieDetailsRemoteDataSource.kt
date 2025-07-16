package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MovieResponse

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieSimilar(movieId: Long): MovieResponse
}