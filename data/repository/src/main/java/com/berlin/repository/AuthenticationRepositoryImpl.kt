package com.berlin.repository

import com.berlin.exception.UnauthorizedException
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.mapper.auth.toDomain
import com.berlin.repository.util.toException
import repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthenticationRepository {

    override suspend fun login(
        userName: String,
        password: String
    ) {
        return try {
            val result =
                remoteDataSource.login(userName, password, requestToken().requestToken).also {
                    localDataSource.saveUserToken(it.requestToken.toString())
                    createSession(it.requestToken.toString())
                }
            result.toDomain()
        } catch (e: UnauthorizedException) {
            throw e.message.toException()
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}