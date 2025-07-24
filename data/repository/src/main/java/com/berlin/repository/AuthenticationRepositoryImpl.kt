package com.berlin.repository

import com.berlin.entity.ValidationException
import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.Session
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.mapper.auth.toDomain
import com.berlin.repository.util.toException
import repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthenticationRepository {
    override suspend fun register(
        email: String,
        userName: String,
        password: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun requestToken(): LoginToken {

        return try {
            remoteDataSource.requestToken().toDomain()
        } catch (e: ValidationException) {
            throw e.message.toException()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createSession(requestToken: String): Session {
        return try {
            val result = remoteDataSource.createSession(requestToken)
            if (result.success) {
                localDataSource.saveUserSessionId(result.sessionId.toString())
            }
            result.toDomain()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(
        userName: String,
        password: String
    ): LoginToken {
        return try {
            val result =
                remoteDataSource.login(userName, password, requestToken().requestToken).also {
                    localDataSource.saveUserToken(it.requestToken.toString())
                }
            result.toDomain()
        } catch (e: ValidationException) {
            throw e.message.toException()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}