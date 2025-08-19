package com.berlin.repository

import com.berlin.entity.Actor
import com.berlin.entity.Episode
import com.berlin.entity.Genre
import com.berlin.entity.MediaImage
import com.berlin.entity.Review
import com.berlin.entity.TVShow
import com.berlin.entity.Video
import com.berlin.exception.NetworkException
import com.berlin.repository.datasource.local.datasource.GenreLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toTVShowGenreEntity
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.tmdbImageUrl
import repository.TVShowDetailsRepository
import javax.inject.Inject


class TvShowDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource,
    private val genreLocalDataSource: GenreLocalDataSource
) : TVShowDetailsRepository {
    override suspend fun getTVShowDetails(tvShowId: Long): TVShow {

        val galleryImages = try {
            getTVShowsImages(tvShowId).backdrops.take(10)
        } catch (e: Exception) {
            emptyList<String>()
        }
        val hasVideo = try {
            getTVShowVideos(tvShowId).isNotEmpty()

        } catch (e: Exception) {
            false
        }
        return remoteDataSource.getTVShowDetailsById(tvShowId).toDomain(
            galleryImages = galleryImages,
            hasVideo = hasVideo,
        )
    }

    override suspend fun getTVShowsImages(id: Long): MediaImage {
        return try {
            val imagesResponse = remoteDataSource.getTVImagesById(id)

            val backdrops = imagesResponse.backdrops?.mapNotNull {
                it.filePath?.let { path ->
                    tmdbImageUrl(
                        path = path, MediaUrls.TmdbImageSize.W500
                    )
                }
            }

            val posters = imagesResponse.posters?.mapNotNull {
                it.filePath?.let { path ->
                    tmdbImageUrl(
                        path = path, MediaUrls.TmdbImageSize.W500
                    )
                }
            }

            MediaImage(backdrops = backdrops.orEmpty(), posters = posters.orEmpty())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTVShowsCastDetails(seriesId: Long): List<Actor> {
        return remoteDataSource.getTVCastDetailsById(
            seriesId
        ).cast?.mapNotNull { castItemDto ->
            castItemDto.toDomain()
        }.orEmpty()
    }

    override suspend fun getTVShowsSimilar(seriesId: Long): List<TVShow> {
        val galleryImages = getTVShowsImages(seriesId).backdrops.take(10)
        val hasVideo = getTVShowVideos(seriesId).isNotEmpty()
        val genreScoresMap = recentlyWatchedLocalDataSource.getCategoryAsPreference()
            .associate { it.categoryId to it.count }

        return remoteDataSource.getSimilarTVById(seriesId).results?.mapNotNull { tvShowDto ->
            tvShowDto.toDomain(
                galleryImages = galleryImages, hasVideo = hasVideo
            )
        }?.sortedByDescending { tvShow ->
            tvShow.genres.sumOf { genre ->
                genreScoresMap[genre.id] ?: 0
            }
        }.orEmpty()
    }

    override suspend fun getTVShowReviews(seriesId: Long): List<Review> {
        return remoteDataSource.getTVShowReviewsById(seriesId).results?.filterNotNull()
            ?.map { reviewDto -> reviewDto.toDomain() }.orEmpty()
    }


    override suspend fun getSeasonEpisodes(
        tvShowId: Long,
        seasonNumber: Int,
    ): List<Episode> {
        return remoteDataSource.getEpisodeSeasonTV(
            tvShowId, seasonNumber
        ).episodes?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun getTVShowsGenres(): List<Genre> {
        return try {
            val remoteGenres = remoteDataSource.getTVGenres().genres
            genreLocalDataSource.cacheTVGenres(remoteGenres.map { it.toTVShowGenreEntity() })
            remoteGenres.map { it.toDomain() }
        } catch (e: NetworkException) {
            return genreLocalDataSource.getCachedTVGenres().map { it.toDomain() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTVShowVideos(seriesId: Long): List<Video> {
        return remoteDataSource.getTVShowVideos(seriesId).results?.mapNotNull {
            it?.toDomain()
        }.orEmpty()
    }
}
