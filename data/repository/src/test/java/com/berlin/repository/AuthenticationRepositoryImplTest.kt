package com.berlin.repository

import com.berlin.entity.auth.LoginToken
import com.berlin.entity.auth.Session
import com.berlin.repository.datasource.local.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.SessionDto
import com.google.common.truth.Truth.assertThat
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class AuthenticationRepositoryImplTest {
    private lateinit var authenticationRepository: AuthenticationRepositoryImpl
    private lateinit var remoteDataSource: AuthenticationRemoteDataSource
    private lateinit var localDataSource: AuthenticationLocalDataSource

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        authenticationRepository = AuthenticationRepositoryImpl(remoteDataSource, localDataSource)

    }

    @Test
    fun `should return request token when requestToken is called`() = runTest {
        //given
        val requestToken = dummyLoginLoginDto
        coEvery { remoteDataSource.requestToken() } returns requestToken

        //when
        val result = authenticationRepository.requestToken()
        //then
        assertThat(result).isEqualTo(dummyLoginToken)
    }

    @Test
    fun `should throw exception when remoteDataSource fails to requestToken`() = runTest {
        // Given
        val expectedException = Exception("Network error")
        coEvery { remoteDataSource.requestToken() } throws expectedException
        //when & Then
        assertThrows<Exception> { authenticationRepository.requestToken() }
    }


    @Test
    fun `should create session when passed valid RequestToken`() = runTest {
        //given
        val requestToken = "0046d05c556e3bdc3cfb786369e05992fb272e2e"

        coEvery { remoteDataSource.createSession(requestToken) } returns dummySessionDto
        coEvery { localDataSource.saveUserSessionId(any()) } returns true
        //when
        val result = authenticationRepository.createSession(requestToken)
        //then
        assertThat(result).isEqualTo(dummySession)

    }

    @Test
    fun `should throw exception when remoteDataSource fails to create session`() = runTest {
        // Given
        val requestToken = "0046d05c556e3bdc3cfb786369e05992fb272e2e"
        val expectedException = Exception("Network error")
        coEvery { remoteDataSource.createSession(requestToken) } throws expectedException
        //when & Then
        assertThrows<Exception> { remoteDataSource.createSession(requestToken) }
    }


    @Test
    fun `should login and save token when credentials are valid`() = runTest {
        // Given

        coEvery { remoteDataSource.login(any(), any(), any()) } returns dummyLoginLoginDto
        coEvery { localDataSource.saveUserToken(any()) } returns true

        // When
        val result = authenticationRepository.login(
            userName = "testUser",
            password = "testPass",
            requestToken = "testToken"
        )

        // Then
        assertThat(result).isEqualTo(dummyLoginToken)
        coVerify {
            remoteDataSource.login(any(), any(), any())
            localDataSource.saveUserToken(any())
        }
    }

    @Test
    fun `should throw when remote login fails`() = runTest {
        // Given
        val expectedException = Exception("Network error")
        coEvery { remoteDataSource.login(any(), any(), any()) } throws expectedException

        // When & Then
        assertThrows<Exception> {
            authenticationRepository.login(
                userName = "testUser",
                password = "testPass",
                requestToken = "testToken"
            )
        }
    }

    @After
    fun tearDown() {
        clearMocks(this)
    }
}

val dummySessionDto = SessionDto(
    success = true,
    sessionId = "ejqrrt6e3bdc3cfb786369e05992fb272e2e1"
)
val dummySession = Session(
    success = true,
    sessionId = "ejqrrt6e3bdc3cfb786369e05992fb272e2e1"
)
val dummyLoginToken = LoginToken(
    success = true,
    expiresAt = "2025-07-23 15:57:05 UTC",
    requestToken = "0046d05c556e3bdc3cfb786369e05992fb272e2e"
)
val dummyLoginLoginDto = LoginDto(
    success = true,
    expiresAt = "2025-07-23 15:57:05 UTC",
    requestToken = "0046d05c556e3bdc3cfb786369e05992fb272e2e"
)

