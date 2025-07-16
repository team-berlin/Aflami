package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.local.dto.MediaImagesResponse

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
}