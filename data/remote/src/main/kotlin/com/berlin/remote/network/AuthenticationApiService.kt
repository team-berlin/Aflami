package com.berlin.remote.network

import com.berlin.remote.network.ApiConstants.SESSION_ID
import com.berlin.repository.datasource.remote.dto.AccountDetailsDto
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.datasource.remote.dto.auth.SessionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationApiService {
    @POST(ApiConstants.CREATE_SESSION_WITH_LOGIN_ENDPOINT)
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): Response<LoginDto>

    @POST(ApiConstants.CREATE_SESSION_ENDPOINT)
    suspend fun createSession(@Body sessionRequest: RequestTokenDTO): Response<SessionDto>

    @GET(ApiConstants.NEW_TOKEN_ENDPOINT)
    suspend fun requestToken(): Response<LoginDto>

    @POST(ApiConstants.DELETE_SESSION_ENDPOINT)
    suspend fun logout(@Body sessionRequest: RequestTokenDTO): Response<Unit>

    @GET("account")
    suspend fun getAccountDetails(
        @Query(SESSION_ID) sessionId: String,
    ): Response<AccountDetailsDto>
}
