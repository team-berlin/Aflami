package usecase.auth

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AuthenticationRepository

class GetLogoutUseCaseTest {
    private val repository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var getLogoutUseCase: GetLogoutUseCase

    @Before
    fun setup() {
        getLogoutUseCase = GetLogoutUseCase(repository)
    }

    @Test
    fun `should call logout function once when invoked`() = runTest {
        getLogoutUseCase()

        coVerify(exactly = 1) { repository.logout() }
    }

    @Test
    fun `should throw exception when logout fails`() = runTest {
        coEvery { repository.logout() } throws Exception(LOGOUT_FAILED)

        assertThrows<Exception> {
            getLogoutUseCase()
        }

        coVerify(exactly = 1) { repository.logout() }
    }

    companion object {
        const val LOGOUT_FAILED = "Logout failed"
    }
}