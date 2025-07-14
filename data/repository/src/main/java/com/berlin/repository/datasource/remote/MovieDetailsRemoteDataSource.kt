package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaCastResponse


interface MovieDetailsRemoteDataSource {
    suspend fun getMovieCastDetails(movieId: Long):MediaCastResponse
}