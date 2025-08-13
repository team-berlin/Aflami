package usecase.profile

import com.berlin.entity.AppTheme
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.SettingsRepository

class SetThemeUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk()
    private val setThemeUseCase: SetThemeUseCase = SetThemeUseCase(settingsRepository)

    @Test
    fun `should call setTheme with correct theme`() = runTest {
        // Arrange
        coEvery { settingsRepository.setTheme(THEME) } just Runs

        // Act
        setThemeUseCase(THEME)

        // Assert
        coVerify(exactly = 1) { settingsRepository.setTheme(THEME) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { settingsRepository.setTheme(THEME) } throws Exception(ERROR_MESSAGE)

        // Act & Assert
        val exception = assertThrows<Exception> { setThemeUseCase(THEME) }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { settingsRepository.setTheme(THEME) }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to set theme"
        val THEME = AppTheme.DARK
    }
}