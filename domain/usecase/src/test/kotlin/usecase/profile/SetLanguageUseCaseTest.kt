package usecase.profile

import com.berlin.entity.AppLanguage
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.SettingsRepository

class SetLanguageUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk()
    private val setLanguageUseCase: SetLanguageUseCase =
        SetLanguageUseCase(settingsRepository)
    
    @Test
    fun `should call setLanguage with correct language`() = runTest {
        // Arrange
        coEvery { settingsRepository.setLanguage(LANGUAGE) } just Runs

        // Act
        setLanguageUseCase(LANGUAGE)

        // Assert
        coVerify(exactly = 1) { settingsRepository.setLanguage(LANGUAGE) }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { settingsRepository.setLanguage(LANGUAGE) } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { setLanguageUseCase(LANGUAGE) }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { settingsRepository.setLanguage(LANGUAGE) }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to set language"
        val LANGUAGE = AppLanguage.EN
    }
}