package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.GenreEntity


interface GenreLocalDataSource {
    suspend fun getCachedGenres(type: String): List<GenreEntity>
    suspend fun cacheGenres(genres: List<GenreEntity>)
    suspend fun clearCachedGenres(type: String)
}