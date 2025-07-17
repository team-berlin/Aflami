package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto

interface TvShowDetailsRemoteDataSource {
    suspend fun getTvShowDetails(id: Long,language: String): TVShowDetailsDto
}