package usecase.tvshow

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository
import usecase.movie.DeleteQueryFromMoviesHistoryUseCaseTest

class DeleteQueryFromTVShowsHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private val deleteQueryFromTVShowsHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase =
        DeleteQueryFromTVShowsHistoryUseCase(tvShowRepository)


    @Test
    fun `should call repository to delete query from TV show history`() = runTest {
        // Arrange
        coEvery {
            tvShowRepository.deleteTVShowQueryFromHistory(DeleteQueryFromMoviesHistoryUseCaseTest.Companion.QUERY)
        } returns Unit

        // Act
        deleteQueryFromTVShowsHistoryUseCase(QUERY)

        //Assert
        coVerify(exactly = 1) { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) } throws Exception()

        // Act
        assertThrows<Exception> {
            deleteQueryFromTVShowsHistoryUseCase(QUERY)
        }

        // Assert
        coVerify(exactly = 1) {
            tvShowRepository.deleteTVShowQueryFromHistory(QUERY)
        }
    }

    companion object {
        const val QUERY = "Stranger Things"
    }
}