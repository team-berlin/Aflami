package usecase.tvshow

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class DeleteQueryFromTVShowsHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private val deleteQueryFromTVShowsHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase =
        DeleteQueryFromTVShowsHistoryUseCase(tvShowRepository)


    @Test
    fun `should call repository to delete query from TV show history`() = runTest {
        // Arrange
        coEvery {
            tvShowRepository.deleteTVShowQueryFromHistory(QUERY)
        } returns Unit

        // Act
        val result = deleteQueryFromTVShowsHistoryUseCase(QUERY)

        //Assert
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) } throws
                Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> {
            deleteQueryFromTVShowsHistoryUseCase(QUERY)
        }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) {
            tvShowRepository.deleteTVShowQueryFromHistory(QUERY)
        }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to delete query"
        const val QUERY = "Stranger Things"
    }
}