package usecase.profile

import com.berlin.entity.AppTheme
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.SettingsRepository

class SetThemeUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)
    private val setThemeUseCase = SetThemeUseCase(settingsRepository)

    @Test
    fun `should call settingsRepository setLanguage with correct language`() = runTest {
        val theme = AppTheme.DARK
        setThemeUseCase(theme)
        coVerify { settingsRepository.setTheme(theme) }
    }
}