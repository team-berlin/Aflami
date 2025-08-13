package usecase.profile

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.SettingsRepository

class GetLanguageUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk()
    private val getLanguageUseCase: GetLanguageUseCase =
        GetLanguageUseCase(settingsRepository)

    @Test
    fun `should return language when repository returns a value`() = runTest {
        // Arrange
        coEvery { settingsRepository.getLanguage() } returns flowOf(LANGUAGE)

        // Act
        val result = getLanguageUseCase().first()

        // Assert
        assertThat(result).isEqualTo(LANGUAGE)
        coVerify(exactly = 1) { settingsRepository.getLanguage() }
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        // Arrange
        coEvery { settingsRepository.getLanguage() } returns flowOf(null)

        // Act
        val result = getLanguageUseCase().first()

        // Assert
        assertThat(result).isNull()
        coVerify(exactly = 1) { settingsRepository.getLanguage() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange

        coEvery { settingsRepository.getLanguage() } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { getLanguageUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { settingsRepository.getLanguage() }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to get language"
        const val LANGUAGE = "en"
    }
}