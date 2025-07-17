package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto

interface TvShowDetailsRemoteDataSource {
    suspend fun getSeriesImages(id: Long): MediaImagesResponse
    suspend fun getTvShowDetails(id: Long,language: String): TVShowDetailsDto
}