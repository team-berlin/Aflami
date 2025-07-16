package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaImagesResponse

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
}