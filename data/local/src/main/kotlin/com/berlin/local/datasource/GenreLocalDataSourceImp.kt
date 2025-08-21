package com.berlin.local.datasource

import com.berlin.local.dao.GenreDao
import com.berlin.repository.datasource.local.datasource.GenreLocalDataSource
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity
import javax.inject.Inject

class GenreLocalDataSourceImpl @Inject constructor(
    private val genreDao: GenreDao
) : GenreLocalDataSource {
    override suspend fun getCachedTVGenres(): List<TVShowGenreEntity> {
        return genreDao.getCachedTVGenres()
    }

    override suspend fun getCachedMovieGenres(): List<MoviesGenreEntity> {
        return genreDao.getCachedMovieGenres()
    }

    override suspend fun cacheTVGenres(genres: List<TVShowGenreEntity>) {
        genreDao.cacheTVGenres(genres)
    }

    override suspend fun cacheMovieGenres(genres: List<MoviesGenreEntity>) {
        genreDao.cacheMovieGenres(genres)
    }

    override suspend fun clearCachedTVGenres() {
        genreDao.clearCachedTVGenres()
    }

    override suspend fun clearCachedMovieGenres() {
        genreDao.clearCachedMovieGenres()
    }
}