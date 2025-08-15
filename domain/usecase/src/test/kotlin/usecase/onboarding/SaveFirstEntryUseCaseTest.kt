package usecase.onboarding

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
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
        val result = saveFirstEntryUseCase()

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { appEntryRepository.saveFirstEntry() }
    }

    @Test
    fun `should throw exception when repository throws exception`() = runTest {
        // Arrange
        coEvery { appEntryRepository.saveFirstEntry() } throws Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { saveFirstEntryUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { appEntryRepository.saveFirstEntry() }
    }

    companion object{
        const val ERROR_MESSAGE = "Failed to save first entry"
    }
}