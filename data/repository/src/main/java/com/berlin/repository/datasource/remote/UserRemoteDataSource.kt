package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.account.AccountDto

interface UserRemoteDataSource {
    suspend fun getUserProfile(sessionId: String): AccountDto
}