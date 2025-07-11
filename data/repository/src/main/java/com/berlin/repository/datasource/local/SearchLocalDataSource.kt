package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.MovieEntity

interface SearchLocalDataSource {
    suspend fun getCachedSearch(query: String): List<MovieEntity>
    suspend fun cacheSearch(searchCaching: List<MovieEntity>)
}