package usecase.auth

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLoginStatusTest {
    private val authenticationRepository: AuthenticationRepository = mockk()
    private var getLoginStatusUseCase: GetLoginUseCase =
        GetLoginUseCase(authenticationRepository)

    @Test
    fun `should return true when user is logged in`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } returns true

        // Act
        val result = getLoginStatusUseCase()

        // Assert
        assertThat(result).isTrue()
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }

    @Test
    fun `should return false when user is not logged in`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } returns false

        // Act
        val result = getLoginStatusUseCase()

        // Assert
        assertThat(result).isFalse()
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }


    @Test
    fun `should throw an exception when the status is not determined`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getLoginStatusUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }

    companion object {
        const val EXCEPTION = "Error in getting status"
    }
}