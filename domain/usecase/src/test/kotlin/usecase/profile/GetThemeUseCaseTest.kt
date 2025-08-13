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

class GetThemeUseCaseTest {

    private val settingsRepository: SettingsRepository = mockk()
    private val getThemeUseCase: GetThemeUseCase = GetThemeUseCase(settingsRepository)

    @Test
    fun `should return theme when repository returns a value`() = runTest {
        // Arrange
        coEvery { settingsRepository.getTheme() } returns flowOf(THEME)

        // Act
        val result = getThemeUseCase().first()

        // Assert
        assertThat(result).isEqualTo(THEME)
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        // Arrange
        coEvery { settingsRepository.getTheme() } returns flowOf(null)

        // Act
        val result = getThemeUseCase().first()

        // Assert
        assertThat(result).isNull()
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { settingsRepository.getTheme() } throws Exception(ERROR_MESSAGE)

        // Act & Assert
        val exception = assertThrows<Exception> { getThemeUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to get theme"
        const val THEME = "dark"
    }
}