package usecase.movie

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieRepository

class SaveRecentMoviesHistoryUseCaseTest {

    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var saveRecentMoviesHistoryUseCase: SaveRecentMoviesHistoryUseCase

    @Before
    fun setUp() {
        saveRecentMoviesHistoryUseCase = SaveRecentMoviesHistoryUseCase(movieRepository)
    }

    @Test
    fun `should call repository to save recent movie query`() = runTest {
        saveRecentMoviesHistoryUseCase(QUERY)

        coVerify(exactly = 1) { movieRepository.saveRecentMoviesHistory(QUERY) }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        coEvery { movieRepository.saveRecentMoviesHistory(QUERY) } throws Exception()

        assertThrows<Exception> {
            saveRecentMoviesHistoryUseCase(QUERY)
        }
    }

    companion object {
        const val QUERY = "Inception"
    }
}