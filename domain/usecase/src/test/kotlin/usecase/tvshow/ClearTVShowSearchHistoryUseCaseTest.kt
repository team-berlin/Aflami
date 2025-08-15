package usecase.tvshow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class ClearTVShowSearchHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk()
    private val clearTVShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase =
        ClearTVShowSearchHistoryUseCase(tvShowRepository)

    @Test
    fun `should call repository to clear TV show search history`() = runTest {
        // Arrange
        coEvery { tvShowRepository.clearTVShowSearchHistory() } returns Unit

        // Act
        val result = clearTVShowSearchHistoryUseCase()

        // Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { tvShowRepository.clearTVShowSearchHistory() }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.clearTVShowSearchHistory() } throws Exception(DB_ERROR)

        // Act
        val exception = assertThrows<Exception> { clearTVShowSearchHistoryUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(DB_ERROR)
        coVerify(exactly = 1) {
            tvShowRepository.clearTVShowSearchHistory()
        }
    }
    companion object{
        const val DB_ERROR = "DB error"
    }
}