package com.berlin.repository.datasource.remote

import com.berlin.repository.datasource.remote.dto.LoginResponse
import com.berlin.repository.datasource.remote.dto.RequestTokenRequestDTO
import com.berlin.repository.datasource.remote.dto.SessionResponse

interface AuthenticationRemoteDataSource {

    suspend fun login(userName: String, password: String): LoginResponse
    suspend fun createSession(): SessionResponse

    suspend fun requestToken(requestTokenRequestDTO: RequestTokenRequestDTO) : LoginResponse

    suspend fun logout()
    suspend fun register(email:String,userName: String, password: String)


}