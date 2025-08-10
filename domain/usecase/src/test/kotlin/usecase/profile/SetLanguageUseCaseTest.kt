package usecase.profile

import com.berlin.entity.AppLanguage
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.SettingsRepository

class SetLanguageUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)
    private val setLanguageUseCase = SetLanguageUseCase(settingsRepository)

    @Test
    fun `should call settingsRepository setLanguage with correct language`() = runTest {
        val language = AppLanguage.EN
        setLanguageUseCase(language)
        coVerify { settingsRepository.setLanguage(language) }
    }
}