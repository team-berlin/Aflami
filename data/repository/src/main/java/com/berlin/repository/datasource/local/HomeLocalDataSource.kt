package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.TVShowHomeEntity

interface HomeLocalDataSource {
    suspend fun getMovies(): List<MovieHomeEntity>
    suspend fun addMovies(movies: List<MovieHomeEntity>)
    suspend fun clearMovies()
    suspend fun getTVShows(): List<TVShowHomeEntity>
    suspend fun addTVShows(tvShows: List<TVShowHomeEntity>)
    suspend fun clearTVShows()
}