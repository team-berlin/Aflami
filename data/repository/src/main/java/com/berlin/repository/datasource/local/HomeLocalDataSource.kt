package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

import com.berlin.repository.datasource.local.dto.SectionHome

interface HomeLocalDataSource {
    suspend fun getMoviesByType(sectionHome: SectionHome): List<MovieHomeEntity>
    suspend fun addMovies(movies: List<MovieHomeEntity>)
    suspend fun clearHomeScreenMovies(sectionHome: SectionHome)
    suspend fun getTVShowsByType(sectionHome: SectionHome): List<TVShowHomeEntity>
    suspend fun addTVShows(tvShows: List<TVShowHomeEntity>)
    suspend fun clearHomeScreenTVShows(sectionHome: SectionHome)
}