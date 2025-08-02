package com.berlin.repository

import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.Actor
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.MediaImage
import com.berlin.entity.Video
import android.util.Log
import com.berlin.exception.AflamiException
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.POSTER_PREFIX
import com.berlin.repository.mapper.toDomain
import repository.TvShowDetailsRepository
import java.time.Instant
import javax.inject.Inject

class TvShowDetailsRepositoryImpl  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : TvShowDetailsRepository {
    override suspend fun getTvShowDetails(tvShowId: Long): TVShow {
        val reviews= getTVShowReviews(tvShowId)
        val galleryImages = getSeriesImages(tvShowId).backdrops.take(10)
        val hasVideo = getTVShowVideos(tvShowId).isNotEmpty()
        val episodes = remoteDataSource.getEpisodeSeasonSeries(tvShowId, 1).episodes?.map { it.toDomain() } ?: emptyList()
        return remoteDataSource.getTvShowDetailsById(tvShowId)
        .toDomain(
            reviews =reviews ,
            galleryImages = galleryImages,
            episodes = episodes,
            hasVideo =hasVideo ,
        )
    }



    override suspend fun getSeriesImages(id: Long): MediaImage {
        return try {
            val imagesResponse = remoteDataSource.getSeriesImagesById(id)

            val backdrops = imagesResponse.backdrops
                ?.mapNotNull { it.filePath?.let { path -> POSTER_PREFIX + path } }

            val posters = imagesResponse.posters
                ?.mapNotNull { it.filePath?.let { path -> POSTER_PREFIX + path } }

            MediaImage(backdrops = backdrops.orEmpty(), posters = posters.orEmpty())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSeriesCastDetails(seriesId: Long): List<Actor> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesSimilar(seriesId: Long): List<TVShow> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviews(id: Long): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun getTVShowActors(tvShowId: Long): List<Actor> {
        return remoteDataSource.getSeriesCastDetailsById(
            seriesId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto?.toDomain()
        } ?: emptyList()
    }

    override suspend fun getSimilarTVShows(tvShowId: Long): List<TVShow> {
        return remoteDataSource.getSimilarSeriesById(tvShowId).results?.mapNotNull { tvShowDto ->
            tvShowDto?.toDomain()
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

    override suspend fun getSeriesGenres(): List<Genre> {
        TODO("Not yet implemented")
    }

    override suspend fun getTVShowGenres(): List<Genre> {
        return remoteDataSource.getSeriesGenres().genres.map { it.toDomain() }
    }

    override suspend fun getTVShowVideos(seriesId: Long): List<Video> {
        return remoteDataSource.getTVShowVideos(seriesId).results?.mapNotNull {
            it?.toDomain()
        } ?: emptyList()
    }

    private fun isExpiredOrEmpty(list: List<GenreEntity>): Boolean {
        return list.isEmpty() || list.any { Instant.now().toEpochMilli() - it.time > Constants.CACHE_TIMEOUT }
    }

}
