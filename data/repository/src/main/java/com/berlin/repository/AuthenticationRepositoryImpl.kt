package com.berlin.repository

import com.berlin.exception.NotFoundException
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
) : AuthenticationRepository {

    private suspend fun requestToken(): String {
        return remoteDataSource.requestToken().requestToken
            ?: throw NotFoundException("Token not found")
    }

    private suspend fun createSession(requestToken: String): String {
        return remoteDataSource.createSession(requestToken).sessionId
            ?: throw NotFoundException("Session not found")
    }

    override suspend fun login(
        userName: String,
        password: String,
    ) {
        val requestToken = requestToken()
        remoteDataSource.login(userName, password, requestToken).also {
            val session =
                createSession(it.requestToken ?: throw NotFoundException("Token not found"))
            localDataSource.saveUserSessionId(session)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val result = localDataSource.getUserSessionId()
        return result != null
    }

    override suspend fun logout() {

    }

}