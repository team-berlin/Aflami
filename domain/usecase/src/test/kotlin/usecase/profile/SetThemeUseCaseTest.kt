package usecase.profile

import com.berlin.entity.AppTheme
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
        coEvery { settingsRepository.setTheme(THEME) } throws Exception()

        // Act & Assert
        assertThrows<Exception> { setThemeUseCase(THEME) }

        // Assert
        coVerify(exactly = 1) { settingsRepository.setTheme(THEME) }
    }

    companion object {
        val THEME = AppTheme.DARK
    }
}