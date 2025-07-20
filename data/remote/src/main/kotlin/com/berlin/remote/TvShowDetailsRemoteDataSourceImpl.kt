package com.berlin.remote

import com.berlin.remote.network.TVShowApiService
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.datasource.remote.dto.MediaCastResponse
import com.berlin.repository.datasource.remote.dto.MediaImagesResponse
import com.berlin.repository.datasource.remote.dto.ReviewResponse
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.TVShowResponse
import com.berlin.repository.datasource.remote.dto.details.EpisodesSeasonDto

class TvShowDetailsRemoteDataSourceImpl(
    private val tvShowApiService: TVShowApiService
) : TvShowDetailsRemoteDataSource {

    override suspend fun getSeriesImages(seriesId: Long): MediaImagesResponse {
        return tvShowApiService.getSeriesImages(seriesId)
    }

    override suspend fun getTvShowDetails(seriesId: Long, language: String): TVShowDetailsDto {
        return tvShowApiService.getTvShowDetails(seriesId, language)
    }

    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): MediaCastResponse {
        return tvShowApiService.getSeriesCastDetails(seriesId, language)
    }

    override suspend fun getSeriesSimilar(seriesId: Long): TVShowResponse {
        return tvShowApiService.getSeriesSimilar(seriesId)
    }

    override suspend fun getReviews(id: Long): ReviewResponse {
        return tvShowApiService.getSeriesReviews(id)
    }

    override suspend fun getEpisodeSeasonSeries(
        seriesId: Long,
        seasonNumber: Int
    ): EpisodesSeasonDto {
        return tvShowApiService.getEpisodeSeasonSeries(seriesId, seasonNumber)
    }
}
