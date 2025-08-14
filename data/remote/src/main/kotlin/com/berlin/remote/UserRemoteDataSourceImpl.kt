package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.UserRemoteDataSource
import com.berlin.repository.datasource.remote.dto.account.UserProfileDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val authenticationLocalDataSource: AuthenticationLocalDataSource
) : UserRemoteDataSource {

    override suspend fun getUserProfile(): UserProfileDto {
        val sessionID = authenticationLocalDataSource.getUserSessionId()
            ?: throw IllegalStateException("Session ID is missing. User might not be logged in.")
        return wrapApiResponse { apiService.getUserProfile(sessionID) }
    }
}