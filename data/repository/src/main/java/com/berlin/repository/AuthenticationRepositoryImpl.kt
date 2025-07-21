package com.berlin.repository

import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.LoginResponse
import com.berlin.repository.datasource.remote.dto.RequestTokenRequestDTO
import repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    //private val localDataSource: AuthenticationLocalDataSource
) :AuthenticationRepository {
    override suspend fun login(userName: String, password: String): LoginResponse {
      return  remoteDataSource.login(userName, password)
    }

    override suspend fun logout() {
      return  remoteDataSource.logout()
    }

    override fun register(email: String, userName: String, password: String) {

    }

    override suspend fun requestToken(requestTokenRequestDTO: RequestTokenRequestDTO): LoginResponse {
        return remoteDataSource.requestToken(requestTokenRequestDTO)
    }
}