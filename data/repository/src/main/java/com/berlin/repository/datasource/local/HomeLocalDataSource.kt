package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

import com.berlin.repository.datasource.local.dto.MediaType

interface HomeLocalDataSource {
    suspend fun getMoviesByType(type: MediaType): List<MovieHomeEntity>
    suspend fun addMovies(movies: List<MovieHomeEntity>)
    suspend fun clearMovies()
    suspend fun getTVShowsByType(type: MediaType): List<TVShowHomeEntity>
    suspend fun addTVShows(tvShows: List<TVShowHomeEntity>)
    suspend fun clearTVShows()
}