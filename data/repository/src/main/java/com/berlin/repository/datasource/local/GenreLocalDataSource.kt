package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity

interface GenreLocalDataSource {
    suspend fun getCachedTVGenres(): List<TVShowGenreEntity>
    suspend fun getCachedMovieGenres(): List<MoviesGenreEntity>
    suspend fun cacheTVGenres(genres: List<TVShowGenreEntity>)
    suspend fun cacheMovieGenres(genres: List<MoviesGenreEntity>)
    suspend fun clearCachedTVGenres()
    suspend fun clearCachedMovieGenres()
}