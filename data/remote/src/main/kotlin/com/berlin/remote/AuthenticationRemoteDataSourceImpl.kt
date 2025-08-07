package com.berlin.remote

import com.berlin.remote.network.AuthenticationApiService
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.AccountDetailsDto
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.datasource.remote.dto.auth.SessionDto
import javax.inject.Inject

class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val authenticationApiService: AuthenticationApiService,
) : AuthenticationRemoteDataSource {
    override suspend fun getUserAccountDetails(sessionId: String): AccountDetailsDto {
        return wrapApiResponse {
            authenticationApiService.getAccountDetails(sessionId = sessionId)
        }
    }

    override suspend fun login(userName: String, password: String, requestToken: String): LoginDto {
        require(userName.isNotBlank()) { "Username cannot be blank" }
        require(password.isNotBlank()) { "Password cannot be blank" }
        require(requestToken.isNotBlank()) { "Request token cannot be blank" }
        return wrapApiResponse {
            authenticationApiService.login(
                LoginRequestDTO(
                    userName = userName,
                    password = password,
                    requestToken = requestToken
                )
            )
        }
    }

    override suspend fun createSession(token: String): SessionDto {
        require(token.isNotBlank()) { "Token cannot be blank" }
        return wrapApiResponse {
            authenticationApiService.createSession(RequestTokenDTO(requestToken = token))
        }
    }

    override suspend fun requestToken(): LoginDto {
        return wrapApiResponse { authenticationApiService.requestToken() }
    }

    override suspend fun logout() {
        return wrapApiResponse {
            authenticationApiService.logout(RequestTokenDTO(requestToken = ""))
        }
    }
}
