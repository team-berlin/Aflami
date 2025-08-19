package com.berlin.repository

import com.berlin.exception.UnauthorizedException
import com.berlin.repository.datasource.local.datasource.AuthenticationLocalDataSource
import com.berlin.repository.datasource.remote.AuthenticationRemoteDataSource
import com.berlin.repository.datasource.remote.dto.auth.LoginDto
import com.berlin.repository.datasource.remote.dto.auth.SessionDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationRepositoryImplTest {

    private lateinit var authenticationRepository: AuthenticationRepositoryImpl
    private lateinit var remoteDataSource: AuthenticationRemoteDataSource
    private lateinit var localDataSource: AuthenticationLocalDataSource

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        authenticationRepository = AuthenticationRepositoryImpl(
            authenticationRemoteDataSource = remoteDataSource,
            authenticationLocalDataSource = localDataSource
        )
    }

    @Test
    fun `observeLoginStatus should return values from localDataSource`() = runTest {
        // Given
        val flow = MutableStateFlow(true)
        every { localDataSource.observeLoginStatus() } returns flow

        // When
        val result = authenticationRepository.observeLoginStatus().first()

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `login should request token, login remotely, create session and save it locally`() =
        runTest {
            // Given
            coEvery { remoteDataSource.requestToken() } returns dummyLoginLoginDto
            coEvery { remoteDataSource.login(any(), any(), any()) } returns dummyLoginLoginDto
            coEvery { remoteDataSource.createSession(any()) } returns dummySessionDto
            coEvery { localDataSource.saveUserSessionId(any()) } returns true

            // When
            authenticationRepository.login("testUser", "testPass")

            // Then
            coVerifySequence {
                remoteDataSource.requestToken()
                remoteDataSource.login("testUser", "testPass", dummyLoginLoginDto.requestToken!!)
                remoteDataSource.createSession(dummyLoginLoginDto.requestToken!!)
            }
        }

    @Test
    fun `login should throw when requestToken fails`() = runTest {
        // Given
        coEvery { remoteDataSource.requestToken() } throws UnauthorizedException("Network error")

        // When / Then
        assertThrows<UnauthorizedException> {
            authenticationRepository.login("testUser", "testPass")
        }
    }

    @Test
    fun `isLoggedIn should return true when session exists`() = runTest {
        // Given
        coEvery { localDataSource.getUserSessionId() } returns "session_123"

        // When
        val result = authenticationRepository.isLoggedIn()

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `isLoggedIn should return false when no session exists`() = runTest {
        // Given
        coEvery { localDataSource.getUserSessionId() } returns null

        // When
        val result = authenticationRepository.isLoggedIn()

        // Then
        assertThat(result).isFalse()
    }

}

val dummySessionDto = SessionDto(
    success = true,
    sessionId = "ejqrrt6e3bdc3cfb786369e05992fb272e2e1"
)

val dummyLoginLoginDto = LoginDto(
    success = true,
    expiresAt = "2025-07-23 15:57:05 UTC",
    requestToken = "0046d05c556e3bdc3cfb786369e05992fb272e2e"
)

