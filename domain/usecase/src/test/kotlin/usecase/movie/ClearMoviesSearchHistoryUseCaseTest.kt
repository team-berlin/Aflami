package usecase.movie

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieRepository

class ClearMoviesSearchHistoryUseCaseTest {

    private val repository: MovieRepository = mockk(relaxed = true)
    private lateinit var clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase

    @Before
    fun setup() {
        clearMoviesSearchHistoryUseCase = ClearMoviesSearchHistoryUseCase(repository)
    }

    @Test
    fun `should call clearMovieSearchHistory once when invoked`() = runTest {
        clearMoviesSearchHistoryUseCase()

        coVerify(exactly = 1) {
            repository.clearMovieSearchHistory()
        }
    }
}