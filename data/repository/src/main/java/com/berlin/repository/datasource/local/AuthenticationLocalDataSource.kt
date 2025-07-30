package com.berlin.repository.datasource.local

interface AuthenticationLocalDataSource {
    suspend fun saveUserToken(userToken: String): Boolean
    suspend fun getUserToken(): String?
    suspend fun deleteUserToken(): Boolean
    suspend fun saveUserSessionId(userSessionId: String): Boolean
    suspend fun getUserSessionId(): String?
    suspend fun deleteUserSessionId(): Boolean
}