package com.berlin.repository

import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.RequestToken
import com.berlin.entity.auth.Session
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.mapper.auth.toDomain
import repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    //private val localDataSource: AuthenticationLocalDataSource
) :AuthenticationRepository {
    override suspend fun register(
        email: String,
        userName: String,
        password: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun requestToken(): LoginToken {
        return remoteDataSource.requestToken().toDomain()
    }

    override suspend fun createSession(requestToken:String): Session {
      return  remoteDataSource.createSession(requestToken).toDomain()
    }

    override suspend fun login(
        userName: String,
        password: String,
        requestToken: String
    ): LoginToken {
       return remoteDataSource.login(userName, password,requestToken).toDomain()
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}