
package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toMovie
import com.berlin.repository.mapper.toDomain
import repository.ContinueWatchingRepository
import javax.inject.Inject

class WatchedMediaRepositoryImpl  @Inject constructor (
    private val localDataSource: RecentlyWatchedLocalDataSource
) : ContinueWatchingRepository {
    override suspend fun getContinueWatchingMovies(page: Int): List<Movie> {
        return localDataSource.getRecentlyWatchedMovie(
            pageSize = 20,
            page = page
        ).map {
            it.toMovie()
        }
    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        localDataSource.addRecentlyWatchedMovie(movie.toLocalEntity())
    }
    override suspend fun getContinueWatchingTVShows(page: Int): List<TVShow> {
        return localDataSource.getRecentlyWatchedTvShow(
            pageSize = 20,
            page = page
        ).map {
            it.toDomain()
        }
    }

    override suspend fun addContinueWatchingTVShow(tvShow: TVShow) {
        localDataSource.addRecentlyWatchedTvShow(tvShow.toLocalEntity())
    }
}