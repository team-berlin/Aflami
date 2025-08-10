package usecase.profile

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.SettingsRepository

class GetThemeUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)
    private val getThemeUseCaseTest = GetThemeUseCase(settingsRepository)

    @Test
    fun `should returns them when call the function`() = runTest {
        val expectedTheme: Flow<String?> = flowOf("DARK")
        coEvery { settingsRepository.getTheme() } returns expectedTheme
        val resalt = getThemeUseCaseTest()
        assertEquals(expectedTheme, resalt)
    }
}