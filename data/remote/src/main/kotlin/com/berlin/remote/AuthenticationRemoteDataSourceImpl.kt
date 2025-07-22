package com.berlin.remote

import com.berlin.remote.network.AuthenticationApiService
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.auth.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.datasource.remote.dto.auth.SessionDto


class AuthenticationRemoteDataSourceImpl(
    private val authenticationApiService: AuthenticationApiService
) : AuthenticationRemoteDataSource {
    override suspend fun login(userName: String, password: String,requestToken: String): LoginDto {
        return authenticationApiService.login(
            LoginRequestDTO(
                userName = userName,
                password = password,
                requestToken=requestToken
            )
        )
    }

    override suspend fun createSession(token: String): SessionDto {
        return authenticationApiService.createSession(
            RequestTokenDTO(token)
        )
    }

    override suspend fun requestToken(): LoginDto {
        return authenticationApiService.requestToken()
    }


    override suspend fun logout() {
        authenticationApiService.logout()
    }

    override suspend fun register(
        email: String,
        userName: String,
        password: String
    ) {
        authenticationApiService.register(email, userName, password)
    }
}