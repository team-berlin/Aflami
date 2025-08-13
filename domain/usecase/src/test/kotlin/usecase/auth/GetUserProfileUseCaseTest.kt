package usecase.auth

import com.berlin.entity.UserProfile
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.UserProfileRepository
import usecase.profile.GetUserProfileUseCase
import org.junit.jupiter.api.assertThrows
import repository.UserRepository

class GetUserProfileUseCaseTest {

    private lateinit var repository: UserProfileRepository
    private lateinit var useCase: GetUserProfileUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetUserProfileUseCase(repository)
    }

    @Test
    fun `invoke should return user profile from repository`() = runTest {
        // Given
        val sessionId = "test_session_id"
        val expectedUser = UserProfile(
            id = 123,
            username = "john_doe",
            name = "John Doe",
            includeAdult = false,
            avatarUrl = "https://image.tmdb.org/t/p/original/abc.jpg",
        )
        coEvery { repository.getUserProfile() } returns expectedUser

        // When
        val result = useCase()

        // Then
        assertThat(result).isEqualTo(expectedUser)
        coVerify(exactly = 1) { repository.getUserProfile() }
    }
}