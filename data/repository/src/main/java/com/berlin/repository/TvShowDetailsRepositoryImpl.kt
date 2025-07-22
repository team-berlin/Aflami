package com.berlin.repository

import com.berlin.entity.Episodes
import com.berlin.entity.MediaCast
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails
import com.berlin.repository.datasource.remote.TvShowDetailsRemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toTVShow
import exceptions.AflamiExceptions
import repository.TvShowDetailsRepository

class TvShowDetailsRepositoryImpl(
    private val remoteDataSource: TvShowDetailsRemoteDataSource,
) : TvShowDetailsRepository {
    override suspend fun getTvShowDetails(id: Long, language: String): TvShowDetails? {
        return try {
            remoteDataSource.getTvShowDetails(id, language).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }

    override suspend fun getSeriesImages(id: Long): List<String> {
        return try {
            remoteDataSource
                .getSeriesImages(seriesId = id)
                .posters
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long, language: String): List<MediaCast> {
        return remoteDataSource.getSeriesCastDetails(
            seriesId,
            language
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getSeriesSimilar(seriesId: Long): List<TVShow> {
        return remoteDataSource.getSeriesSimilar(seriesId).results?.mapNotNull { tvShowDto ->
            tvShowDto?.toTVShow()
        } ?: emptyList()
    }

    override suspend fun getReviews(id: Long): List<Review> {
        return remoteDataSource.getReviews(id).results
            ?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() }
            ?: emptyList()
    }

    override suspend fun getSeasonEpisodes(
        seriesId: Long,
        seasonNumber: Int,
    ): List<Episodes?> {
        return remoteDataSource.getEpisodeSeasonSeries(seriesId, seasonNumber)
            .toDomain()
            .episodes ?: emptyList()
    }
}