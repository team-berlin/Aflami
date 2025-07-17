package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaImagesResponse

import com.berlin.repository.datasource.remote.dto.MovieDetailsDto

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(id: Long,language: String): MovieDetailsDto
}