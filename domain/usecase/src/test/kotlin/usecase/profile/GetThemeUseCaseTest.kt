package usecase.profile

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
        getThemeUseCase().first()

        // Assert
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    @Test
    fun `should return null when repository returns null`() = runTest {
        // Arrange
        coEvery { settingsRepository.getTheme() } returns flowOf(null)

        // Act
        val result = getThemeUseCase().first()

        // Assert
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { settingsRepository.getTheme() } throws Exception()

        // Act & Assert
        assertThrows<Exception> { getThemeUseCase() }

        // Assert
        coVerify(exactly = 1) { settingsRepository.getTheme() }
    }

    companion object {
        const val THEME = "dark"
    }
}