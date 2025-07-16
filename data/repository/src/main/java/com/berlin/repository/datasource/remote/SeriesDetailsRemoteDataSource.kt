package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.local.dto.MediaImagesResponse

interface SeriesDetailsRemoteDataSource {
    suspend fun getSeriesImages(id: Long): MediaImagesResponse
}