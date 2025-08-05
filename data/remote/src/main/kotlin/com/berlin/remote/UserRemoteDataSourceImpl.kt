package com.berlin.remote

import com.berlin.remote.network.ApiService
import com.berlin.repository.datasource.remote.UserRemoteDataSource
import com.berlin.repository.datasource.remote.dto.account.AccountDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : UserRemoteDataSource {

    override suspend fun getUserProfile(sessionId: String): AccountDto {
        return wrapApiResponse { apiService.getUserProfile(sessionId) }
    }
}