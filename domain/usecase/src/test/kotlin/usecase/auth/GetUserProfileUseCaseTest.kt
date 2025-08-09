package usecase.auth

import com.berlin.entity.UserProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.UserRepository

class GetUserProfileUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val getUserProfileUseCase: GetUserProfileUseCase = GetUserProfileUseCase(userRepository)

    @Test
    fun `invoke should return user profile from repository`() = runTest {
        // Arrange
        coEvery { userRepository.getUserProfile(SESSION_ID) } returns EXPECTED_USER

        // Act
        getUserProfileUseCase(SESSION_ID)

        // Assert
        coVerify(exactly = 1) { userRepository.getUserProfile(SESSION_ID) }
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        coEvery { userRepository.getUserProfile(SESSION_ID) } throws Exception(EXCEPTION)

        // Act
        assertThrows<Exception> { getUserProfileUseCase(SESSION_ID) }

        // Assert
        coVerify(exactly = 1) { userRepository.getUserProfile(SESSION_ID) }
    }

    companion object {
        const val SESSION_ID = "test_session_id"

        var EXPECTED_USER = UserProfile(
            id = 123,
            username = "john_doe",
            name = "John Doe",
            includeAdult = false,
            avatarUrl = "https://image.tmdb.org/t/p/original/abc.jpg",
            countryCodeIso6391 = "",
            countryCodeIso31661 = ""
        )

        const val EXCEPTION = "Error in repository"
    }
}