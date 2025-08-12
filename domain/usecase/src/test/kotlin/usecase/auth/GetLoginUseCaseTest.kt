package usecase.auth

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLoginUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk()
    private val getLoginUseCase: GetLoginUseCase = GetLoginUseCase(authenticationRepository)


    @Test
    fun `should call loginUseCases when the parameters correct`() = runTest {
        // Arrange
        coEvery { authenticationRepository.login(USERNAME, PASSWORD) } returns Unit

        // Act
        val result = getLoginUseCase(USERNAME, PASSWORD)

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { authenticationRepository.login(USERNAME, PASSWORD) }
    }

    @Test
    fun `should throw exception when the parameters are not correct`() = runTest {
        // Arrange
        coEvery {
            authenticationRepository.login(USERNAME, PASSWORD)
        } throws Exception(INVALID_CREDENTIALS)

        // Act
        assertThrows<Exception> {
            getLoginUseCase(USERNAME, PASSWORD)
        }

        // Assert
        coVerify(exactly = 1) {
            authenticationRepository.login(USERNAME, PASSWORD)
        }
    }

    companion object {
        const val USERNAME = "user"
        const val PASSWORD = "password123"
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}