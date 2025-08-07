package usecase.tvshow

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class SaveRecentTVShowsHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private lateinit var saveRecentTVShowsHistoryUseCase: SaveRecentTVShowsHistoryUseCase

    @Before
    fun setUp() {
        saveRecentTVShowsHistoryUseCase = SaveRecentTVShowsHistoryUseCase(tvShowRepository)
    }

    @Test
    fun `should call repository to save recent TV show query`() = runTest {
        saveRecentTVShowsHistoryUseCase(QUERY)

        coVerify(exactly = 1) { tvShowRepository.saveRecentTVShowsHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        coEvery { tvShowRepository.saveRecentTVShowsHistory(QUERY) } throws Exception()

        assertThrows<Exception> {
            saveRecentTVShowsHistoryUseCase(QUERY)
        }
    }

    companion object {
        const val QUERY = "Breaking Bad"
    }
}