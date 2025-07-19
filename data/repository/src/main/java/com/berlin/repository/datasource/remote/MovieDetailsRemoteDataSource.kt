package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MovieResponse

import com.berlin.repository.datasource.remote.dto.MediaCastResponse


import com.berlin.repository.datasource.remote.dto.MediaImagesResponse

import com.berlin.repository.datasource.remote.dto.MovieDetailsDto

import com.berlin.repository.datasource.remote.dto.ReviewResponse

interface MovieDetailsRemoteDataSource {
    suspend fun getMovieSimilar(movieId: Long): MovieResponse
    suspend fun getMovieImages(movieId: Long): MediaImagesResponse
    suspend fun getMovieDetails(id: Long,language: String): MovieDetailsDto
    suspend fun getMovieCastDetails(movieId: Long, language: String): MediaCastResponse
    suspend fun getReviews(id: Long): ReviewResponse
}