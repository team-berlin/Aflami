package com.berlin.remote.network

import com.berlin.repository.datasource.remote.dto.LoginRequestDTO
import com.berlin.repository.datasource.remote.dto.LoginResponse
import com.berlin.repository.datasource.remote.dto.RequestTokenRequestDTO
import com.berlin.repository.datasource.remote.dto.SessionResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthenticationApiService {

    @POST(ApiConstants.CREATE_SESSION_WITH_LOGIN_ENDPOINT)
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponse

    @POST
    suspend fun createSession(): SessionResponse

    @POST
    suspend fun requestToken(@Body requestTokenRequestDTO: RequestTokenRequestDTO) : LoginResponse

    @POST
    suspend fun logout()

    @POST
    suspend fun register(email:String,userName: String, password: String)

}
