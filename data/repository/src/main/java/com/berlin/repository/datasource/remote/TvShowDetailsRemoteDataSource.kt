package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse

interface TvShowDetailsRemoteDataSource {
    suspend fun getSeriesImages(id: Long): MediaImagesResponse
    suspend fun getTvShowDetails(id: Long,language: String): TVShowDetailsDto
    suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse
    suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse
}