package usecase.auth

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLogoutUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk()
    private val getLogoutUseCase: GetLogoutUseCase = GetLogoutUseCase(authenticationRepository)


    @Test
    fun `should call logout function once when invoked`() = runTest {
        // Arrange
        coEvery { authenticationRepository.logout() } returns Unit

        // Act
        val result = getLogoutUseCase()

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { authenticationRepository.logout() }
    }

    @Test
    fun `should throw exception when logout fails`() = runTest {
        // Arrange
        coEvery { authenticationRepository.logout() } throws Exception(LOGOUT_FAILED)

        // Act
        assertThrows<Exception> { getLogoutUseCase() }

        // Assert
        coVerify(exactly = 1) { authenticationRepository.logout() }
    }

    companion object {
        const val LOGOUT_FAILED = "Logout failed"
    }
}