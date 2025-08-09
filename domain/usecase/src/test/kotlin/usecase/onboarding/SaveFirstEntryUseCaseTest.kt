package usecase.onboarding

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.AppEntryRepository

class SaveFirstEntryUseCaseTest {
    private val appEntryRepository: AppEntryRepository = mockk()
    private val saveFirstEntryUseCase: SaveFirstEntryUseCase =
        SaveFirstEntryUseCase(appEntryRepository)


    @Test
    fun `should call saveFirstEntry once`() = runTest {
        // Arrange
        coEvery { appEntryRepository.saveFirstEntry() } returns Unit

        // Act
        saveFirstEntryUseCase()

        // Assert
        coVerify(exactly = 1) { appEntryRepository.saveFirstEntry() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { appEntryRepository.saveFirstEntry() } throws Exception()

        // Act
        assertThrows<Exception> { saveFirstEntryUseCase() }

        // Assert
        coVerify(exactly = 1) { appEntryRepository.saveFirstEntry() }
    }
}