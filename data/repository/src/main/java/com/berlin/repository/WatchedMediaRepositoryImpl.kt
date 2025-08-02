
package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toMovie
import com.berlin.repository.mapper.toTVShow
import repository.ContinueWatchingRepository
import javax.inject.Inject

class WatchedMediaRepositoryImpl  @Inject constructor (
    private val localDataSource: ContinueWatchingLocalDataSource
) : ContinueWatchingRepository {
    override suspend fun getContinueWatchingMovies(page: Int): List<Movie> {
        return localDataSource.getContinueWatchingMovie(
            pageSize = 20,
            page = page
        ).map {
            it.toMovie()
        }
    }

    override suspend fun addContinueWatchingMovie(movie: Movie) {
        localDataSource.addContinueWatchedMovie(movie.toLocalEntity())
    }
    override suspend fun getContinueWatchingTVShows(page: Int): List<TVShow> {
        return localDataSource.getContinueWatchedTVShow(
            pageSize = 20,
            page = page
        ).map {
            it.toTVShow()
        }
    }

    override suspend fun addContinueWatchingTVShow(tvShow: TVShow) {
        localDataSource.addContinueWatchedTVShow(tvShow.toLocalEntity())
    }
}