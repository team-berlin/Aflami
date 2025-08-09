package com.berlin.repository

import com.berlin.exception.NotFoundException
import com.berlin.repository.datasource.local.UserLocalDataSource
import com.berlin.repository.datasource.remote.UserRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val remote = mockk<UserRemoteDataSource>()
    private val local = mockk<UserLocalDataSource>(relaxed = true)
    private val repo = UserRepositoryImpl(remote, local)

    @Test
    fun `getUserProfile propagates remote exceptions`() = runTest {
        coEvery { remote.getUserProfile(any()) } throws NotFoundException("Network down")

        assertThrows<NotFoundException> {
            repo.getUserProfile("session_123")
        }
    }

    @Test
    fun `getUserLocally returns null when local has no data`() = runTest {
        coEvery { local.getUser() } returns null

        val result = repo.getUserLocally()
        assertNull(result)
    }

}
