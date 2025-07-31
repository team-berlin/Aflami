package com.berlin.repository

import com.berlin.entity.Episodes
import com.berlin.entity.Genre
import com.berlin.entity.MediaCast
import com.berlin.entity.MediaImage
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.TvShowDetails
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.dto.GenreEntity
import com.berlin.repository.datasource.local.dto.SearchingEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toGenreEntity
import com.berlin.repository.mapper.toTVShow
import com.berlin.repository.util.Constants
import com.berlin.repository.util.Constants.GENRE_TYPE_TV
import exceptions.AflamiExceptions
import repository.TvShowDetailsRepository
import java.time.Instant

class TvShowDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val genreLocalDataSource: GenreLocalDataSource
) : TvShowDetailsRepository {


    override suspend fun getTvShowDetails(id: Long): TvShowDetails? {
        return try {
            remoteDataSource.getTvShowDetails(id).toDomain()
        } catch (exception: AflamiExceptions) {
            throw exception
        }
    }

    override suspend fun getSeriesImages(id: Long): MediaImage {
        return try {
            val imagesResponse = remoteDataSource.getSeriesImages(seriesId = id)

            val backdrops = imagesResponse.backdrops
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()

            val posters = imagesResponse.posters
                ?.map { POSTER_PREFIX + it.filePath }
                ?: throw Exception()
            MediaImage(
                backdrops = backdrops,
                posters = posters)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long): List<MediaCast> {
        return remoteDataSource.getSeriesCastDetails(
            seriesId
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
        return remoteDataSource.getMovieReviews(id).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() } ?: emptyList()
    }

    override suspend fun getSeasonEpisodes(
        seriesId: Long,
        seasonNumber: Int,
    ): List<Episodes?> {
        return remoteDataSource.getEpisodeSeasonSeries(seriesId, seasonNumber).toDomain().episodes
            ?: emptyList()
    }

    override suspend fun getSeriesGenres(): List<Genre> {
        val cachedGenres = genreLocalDataSource.getCachedGenres(GENRE_TYPE_TV)
        if (!isExpiredOrEmpty(cachedGenres)) {
            return cachedGenres.map { it.toDomain() }
        }

        val genres = remoteDataSource.getSeriesGenres().genres.map { it.toGenreEntity(GENRE_TYPE_TV) }
        genreLocalDataSource.cacheGenres(genres)
        return genres.map { it.toDomain() }
    }
    private fun isExpiredOrEmpty(list: List<GenreEntity>): Boolean {
        return list.isEmpty() || list.any { Instant.now().toEpochMilli() - it.time > Constants.CACHE_TIMEOUT }
    }

}
