package usecase.tvshow

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class DeleteQueryFromTVShowsHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private lateinit var deleteQueryFromTVShowsHistoryUseCase: DeleteQueryFromTVShowsHistoryUseCase

    @Before
    fun setUp() {
        deleteQueryFromTVShowsHistoryUseCase = DeleteQueryFromTVShowsHistoryUseCase(tvShowRepository)
    }

    @Test
    fun `should call repository to delete query from TV show history`() = runTest {
        deleteQueryFromTVShowsHistoryUseCase(QUERY)

        coVerify(exactly = 1) { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        coEvery { tvShowRepository.deleteTVShowQueryFromHistory(QUERY) } throws Exception()

        assertThrows<Exception> {
            deleteQueryFromTVShowsHistoryUseCase(QUERY)
        }
    }

    companion object {
        const val QUERY = "Stranger Things"
    }
}