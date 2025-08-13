package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.account.UserProfileDto

interface UserRemoteDataSource {
    suspend fun getUserProfile(): UserProfileDto
}