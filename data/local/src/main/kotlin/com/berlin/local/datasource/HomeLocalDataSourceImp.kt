package com.berlin.local.datasource

import com.berlin.local.dao.MediaHomeDao
import com.berlin.repository.datasource.local.HomeLocalDataSource
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity
import javax.inject.Inject

class HomeLocalDataSourceImp @Inject constructor(
    private val mediaHomeDao: MediaHomeDao
) : HomeLocalDataSource {
    override suspend fun getMovies(): List<MovieHomeEntity> {
        return mediaHomeDao.getMovies()
    }

    override suspend fun addMovies(movies: List<MovieHomeEntity>) {
        mediaHomeDao.insertMovies(movies)
    }

    override suspend fun clearMovies() {
        mediaHomeDao.clearMovies()
    }

    override suspend fun getTVShows(): List<TVShowHomeEntity> {
        return mediaHomeDao.getTVShows()
    }

    override suspend fun addTVShows(tvShows: List<TVShowHomeEntity>) {
        mediaHomeDao.insertTVShows(tvShows)
    }

    override suspend fun clearTVShows() {
        mediaHomeDao.clearTVShows()
    }
}