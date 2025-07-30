package usecase.tvshow

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowRepository

class ClearTVShowSearchHistoryUseCaseTest {

    private val tvShowRepository: TVShowRepository = mockk(relaxed = true)
    private lateinit var clearTVShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase

    @Before
    fun setUp() {
        clearTVShowSearchHistoryUseCase = ClearTVShowSearchHistoryUseCase(tvShowRepository)
    }

    @Test
    fun `should call repository to clear TV show search history`() = runTest {
        clearTVShowSearchHistoryUseCase()

        coVerify(exactly = 1) { tvShowRepository.clearTVShowSearchHistory() }
    }

    @Test
    fun `should throw exception if repository throws exception`() = runTest {
        coEvery { tvShowRepository.clearTVShowSearchHistory() } throws Exception()

        assertThrows<Exception> {
            clearTVShowSearchHistoryUseCase()
        }
    }
}