package usecase.onboarding

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.AppEntryRepository

class GetFirstEntryUseCaseTest {
    private val appEntryRepository: AppEntryRepository = mockk()
    private val getFirstEntryUseCase: GetFirstEntryUseCase =
        GetFirstEntryUseCase(appEntryRepository)

    @Test
    fun `should return true when repository returns true`() = runTest {
        // Arrange
        coEvery { appEntryRepository.isFirstEntry() } returns true

        // Act
        val result = getFirstEntryUseCase()

        // Assert
        assertThat(result).isTrue()
        coVerify(exactly = 1) { appEntryRepository.isFirstEntry() }
    }

    @Test
    fun `should return false when repository returns false`() = runTest {
        // Arrange
        coEvery { appEntryRepository.isFirstEntry() } returns false

        // Act
        val result = getFirstEntryUseCase()

        // Assert
        assertThat(result).isFalse()
        coVerify(exactly = 1) { appEntryRepository.isFirstEntry() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { appEntryRepository.isFirstEntry() } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { getFirstEntryUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { appEntryRepository.isFirstEntry() }
    }

    companion object{
        const val ERROR_MESSAGE = "Failed to get first entry"
    }
}