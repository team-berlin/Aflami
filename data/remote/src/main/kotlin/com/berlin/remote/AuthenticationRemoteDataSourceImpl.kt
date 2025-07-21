package com.berlin.remote

import com.berlin.remote.network.AuthenticationApiService
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.LoginResponse
import com.berlin.repository.datasource.remote.dto.RequestTokenRequestDTO
import com.berlin.repository.datasource.remote.dto.SessionResponse


class AuthenticationRemoteDataSourceImpl(
    private val authenticationApiService: AuthenticationApiService
) : AuthenticationRemoteDataSource {
    override suspend fun login(userName: String, password: String): LoginResponse {
        return authenticationApiService.login(
            LoginRequestDTO(
                userName = userName,
                password = password
            )
        )
    }

    override suspend fun createSession(): SessionResponse {
        return authenticationApiService.createSession()
    }

    override suspend fun requestToken(requestTokenRequestDTO: RequestTokenRequestDTO): LoginResponse {
        return authenticationApiService.requestToken(requestTokenRequestDTO)
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