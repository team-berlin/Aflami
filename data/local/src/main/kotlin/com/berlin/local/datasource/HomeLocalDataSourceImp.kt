package com.berlin.local.datasource

import com.berlin.local.dao.HomeMovieDao
import com.berlin.local.dao.HomeTVShowDao
import com.berlin.repository.datasource.local.datasource.HomeLocalDataSource
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity
import javax.inject.Inject


class HomeLocalDataSourceImp @Inject constructor(
    private val homeMovieDao: HomeMovieDao,
    private val homeTVShowDao: HomeTVShowDao
) : HomeLocalDataSource {

    override suspend fun getMoviesBySection(homeSection: HomeSection): List<HomeMovieEntity> {
        return homeMovieDao.getMoviesBySection(homeSection)
    }

    override suspend fun getUpcomingMoviesByGenre(
        homeSection: HomeSection,
        genreId: Long?
    ): List<HomeMovieEntity> {
       return homeMovieDao.getUpcomingMoviesByGenre(homeSection,genreId)
    }

    override suspend fun addMovies(movies: List<HomeMovieEntity>) {
        homeMovieDao.insertMovies(movies)
    }

    override suspend fun  clearHomeScreenMovies(homeSection: HomeSection) {
        homeMovieDao.clearHomeScreenMovies(homeSection)
    }

    override suspend fun getTVShowsBySection(homeSection: HomeSection): List<HomeTVShowEntity> {
        return homeTVShowDao.getTVShowsBySection(homeSection)
    }

    override suspend fun addTVShows(tvShows: List<HomeTVShowEntity>) {
        homeTVShowDao.insertTVShows(tvShows)
    }

    override suspend fun clearHomeScreenTVShows(homeSection: HomeSection) {
        homeTVShowDao.clearHomeScreenTVShows(homeSection)
    }
}