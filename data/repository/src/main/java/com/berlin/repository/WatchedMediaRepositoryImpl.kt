package com.berlin.repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.ContinueWatchingLocalDataSource
import com.berlin.repository.mapper.toLocalEntity
import com.berlin.repository.mapper.toMovie
import com.berlin.repository.mapper.toTVShow
import repository.ContinueWatchingRepository

class WatchedMediaRepositoryImpl (
    private val localDataSource: ContinueWatchingLocalDataSource,

    ): ContinueWatchingRepository {
    override suspend fun getContinueWatchingMovies(): List<Movie> {
        try {
            return localDataSource.getContinueWatchedMovie().map {
                it.toMovie()
            }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun addContinueWatchingMovie(movie: Movie) {
         localDataSource.addContinueWatchedMovie(movie.toLocalEntity())
    }

    override suspend fun getContinueWatchingTVShows(): List<TVShow> {
        try {
            return localDataSource.getContinueWatchedTVShow().map {
                it.toTVShow()
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun addContinueWatchingTVShow(tvShow: TVShow) {
        try {
            localDataSource.addContinueWatchedTVShow(tvShow.toLocalEntity())
        }catch (e:Exception){
            throw e
        }catch (e:Exception){
            throw e
        }
    }


}