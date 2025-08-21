package com.berlin.repository.datasource.local.datasource

import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity

interface HomeLocalDataSource {
    suspend fun getMoviesBySection(homeSection: HomeSection): List<HomeMovieEntity>
    suspend fun getUpcomingMoviesByGenre(homeSection: HomeSection, genreId: Long?): List<HomeMovieEntity>
    suspend fun addMovies(movies: List<HomeMovieEntity>)
    suspend fun clearHomeScreenMovies(homeSection: HomeSection)
    suspend fun getTVShowsBySection(homeSection: HomeSection): List<HomeTVShowEntity>
    suspend fun addTVShows(tvShows: List<HomeTVShowEntity>)
    suspend fun clearHomeScreenTVShows(homeSection: HomeSection)
}