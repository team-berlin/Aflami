package usecase.auth

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLoginUseCaseTest {
    private val repository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var getLoginUseCase: GetLoginUseCase

    @Before
    fun setup() {
        getLoginUseCase = GetLoginUseCase(repository)
    }

    @Test
    fun `should call loginUseCases when the parameters correct`() = runTest {
        getLoginUseCase(USERNAME, PASSWORD)

        coVerify(exactly = 1) { repository.login(USERNAME, PASSWORD) }
    }

    @Test
    fun `should throw exception when the parameters are not correct`() = runTest {
        coEvery {
            repository.login(USERNAME, PASSWORD)
        } throws Exception(INVALID_CREDENTIALS)

        assertThrows<Exception> {
            getLoginUseCase(USERNAME, PASSWORD)
        }

        coVerify(exactly = 1) { repository.login(USERNAME, PASSWORD) }
    }

    companion object {
        const val USERNAME = "user"
        const val PASSWORD = "password123"
        const val INVALID_CREDENTIALS = "Invalid credentials"
    }
}