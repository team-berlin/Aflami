package com.berlin.repository.datasource.local

import kotlinx.coroutines.flow.Flow

interface AuthenticationLocalDataSource {
    fun observeLoginStatus(): Flow<Boolean>
    suspend fun saveUserToken(userToken: String): Boolean
    suspend fun getUserToken(): String?
    suspend fun deleteUserToken(): Boolean
    suspend fun saveUserSessionId(userSessionId: String): Boolean
    suspend fun getUserSessionId(): String?
    suspend fun deleteUserSessionId(): Boolean
}