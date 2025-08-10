package usecase.profile

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.SettingsRepository

class GetLanguageUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)
    private val getLanguageUseCase = GetLanguageUseCase(settingsRepository)

    @Test
    fun `should returns language when call the function`() = runTest {
        val expectedLanguage: Flow<String?> = flowOf("ar")
        coEvery { settingsRepository.getLanguage() } returns expectedLanguage
        val resalt = getLanguageUseCase()
        assertEquals(expectedLanguage, resalt)
    }
}