package com.berlin.repository

import com.berlin.exception.NotFoundException
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
) : AuthenticationRepository {

    override fun observeLoginStatus(): Flow<Boolean> {
        return authenticationLocalDataSource.observeLoginStatus()
    }

    private suspend fun requestToken(): String {
        return authenticationRemoteDataSource.requestToken().requestToken
            ?: throw NotFoundException("Token not found")
    }

    private suspend fun createSession(requestToken: String): String {
        return authenticationRemoteDataSource.createSession(requestToken).sessionId
            ?: throw NotFoundException("Session not found")
    }

    override suspend fun login(
        userName: String,
        password: String,
    ) {
        val requestToken = requestToken()
        authenticationRemoteDataSource.login(userName, password, requestToken).also {
            val session: String =
                createSession(it.requestToken ?: throw NotFoundException("Token not found"))
            val accountDetails = authenticationRemoteDataSource.getUserAccountDetails(session)
            authenticationLocalDataSource.saveUserAccountId(accountDetails.id!!)
            authenticationLocalDataSource.saveUserSessionId(session)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val result = authenticationLocalDataSource.getUserSessionId()
        return result != null
    }

    override suspend fun logout() {
        authenticationLocalDataSource.deleteUserSessionId()
    }

}