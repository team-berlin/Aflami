package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.auth.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.RequestTokenDTO
import com.berlin.repository.datasource.remote.dto.auth.SessionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthenticationApiService {

    @POST(ApiConstants.CREATE_SESSION_WITH_LOGIN_ENDPOINT)
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): Response<LoginDto>

    @POST(ApiConstants.CREATE_SESSION_ENDPOINT)
    suspend fun createSession(@Body token: RequestTokenDTO):  Response<SessionDto>

    @GET(ApiConstants.NEW_TOKEN_ENDPOINT)
    suspend fun requestToken() : Response<LoginDto>

    @POST
    suspend fun logout()

    @POST
    suspend fun register(email:String,userName: String, password: String)

}
