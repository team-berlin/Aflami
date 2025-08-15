package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

interface HomeLocalDataSource {
    suspend fun getMoviesBySection(sectionHome: SectionHome): List<MovieHomeEntity>
    suspend fun addMovies(movies: List<MovieHomeEntity>)
    suspend fun clearHomeScreenMovies(sectionHome: SectionHome)
    suspend fun getTVShowsBySection(sectionHome: SectionHome): List<TVShowHomeEntity>
    suspend fun addTVShows(tvShows: List<TVShowHomeEntity>)
    suspend fun clearHomeScreenTVShows(sectionHome: SectionHome)
}