package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.account.UserProfileDto
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.SessionDto

interface AuthenticationRemoteDataSource {
    suspend fun login(userName: String, password: String, requestToken: String): LoginDto
    suspend fun getUserAccountDetails(sessionId: String): UserProfileDto
    suspend fun createSession(token: String): SessionDto
    suspend fun requestToken(): LoginDto
    suspend fun logout()


}