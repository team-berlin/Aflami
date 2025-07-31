package com.berlin.repository

import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.Actor
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toTVShow
import exceptions.AflamiExceptions
import repository.TVShowDetailsRepository

class TvShowDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : TVShowDetailsRepository {
    override suspend fun getTVShowDetails(tvShowId: Long): TVShow? {
        return try {
            remoteDataSource.getTvShowDetails(id).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }

    override suspend fun getTVShowGallery(tvShowId: Long): List<String> {
        return try {
            remoteDataSource.getSeriesImages(seriesId = tvShowId).posters?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTVShowActors(tvShowId: Long): List<Actor> {
        return remoteDataSource.getSeriesCastDetails(
            seriesId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getSimilarTVShows(tvShowId: Long): List<TVShow> {
        return remoteDataSource.getSeriesSimilar(tvShowId).results?.mapNotNull { tvShowDto ->
            tvShowDto?.toTVShow()
        } ?: emptyList()
    }

    override suspend fun getTVShowReviews(tvShowId: Long): List<Review> {
        return remoteDataSource.getMovieReviews(tvShowId).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() } ?: emptyList()
    }

    override suspend fun getSeasonEpisodes(
        tvShowId: Long,
        seasonNumber: Int,
    ): List<Episode?> {
        return remoteDataSource.getEpisodeSeasonSeries(tvShowId, seasonNumber).toDomain().episodes
            ?: emptyList()
    }

    override suspend fun getTVShowGenres(): List<Genre> {
        return remoteDataSource.getSeriesGenres().genres.map { it.toDomain() }
    }
}