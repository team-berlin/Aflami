package com.berlin.repository

import android.util.Log
import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.RequestToken
import com.berlin.entity.auth.Session
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.mapper.auth.toDomain
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
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun createSession(requestToken: String): Session {
        return try {
           val result = remoteDataSource.createSession(requestToken)
            if (result.success){
                localDataSource.saveUserSessionId(result.sessionId.toString())
            }
            result.toDomain()
        }catch (e: Exception) {
            Log.d("LOGIN EXCEPTION", "login: $e")
            throw e
        }
    }

    override suspend fun login(
        userName: String,
        password: String,
        requestToken: String
    ): LoginToken {
        return try {
            val result = remoteDataSource.login(userName, password, requestToken)
            if (result.success) {
                localDataSource.saveUserToken(result.requestToken)
            }
            result.toDomain()
        } catch (e: Exception) {
            Log.d("LOGIN EXCEPTION", "login: $e")
            throw e
        }

    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}