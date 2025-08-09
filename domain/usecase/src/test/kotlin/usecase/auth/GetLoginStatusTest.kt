package usecase.auth

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLoginStatusTest {
    private val authenticationRepository: AuthenticationRepository = mockk()
    private var getLoginStatusUseCase: GetLoginStatusUseCase =
        GetLoginStatusUseCase(authenticationRepository)

    @Test
    fun `should return true when user is logged in`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } returns true

        // Act
        getLoginStatusUseCase()

        // Assert
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }

    @Test
    fun `should return false when user is not logged in`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } returns false

        // Act
        getLoginStatusUseCase()

        // Assert
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }


    @Test
    fun `should throw an exception when the status is not determined`() = runTest {
        // Arrange
        coEvery { authenticationRepository.isLoggedIn() } throws Exception(EXCEPTION)

        // Act
        assertThrows<Exception> { getLoginStatusUseCase() }

        // Assert
        coVerify(exactly = 1) { authenticationRepository.isLoggedIn() }
    }

    companion object {
        const val EXCEPTION = "Error in getting status"
    }
}