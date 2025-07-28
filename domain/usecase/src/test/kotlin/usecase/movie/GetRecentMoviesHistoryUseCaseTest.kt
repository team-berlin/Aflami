package usecase.movie

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieRepository

class GetRecentMoviesHistoryUseCaseTest {
    private var repository: MovieRepository = mockk()
    private lateinit var getRecentMoviesHistoryUseCase: GetRecentMoviesHistoryUseCase

    @Before
    fun setUp() {
        getRecentMoviesHistoryUseCase = GetRecentMoviesHistoryUseCase(repository)
    }

    @Test
    fun `invoke should return recent search queries from repository`() = runTest {
        coEvery { repository.getRecentMoviesSearchQueries() } returns QUERIES

        val result = getRecentMoviesHistoryUseCase()

        assertThat(result).isEqualTo(QUERIES)
    }

    companion object{
        val QUERIES = listOf("Inception", "Matrix", "Oppenheimer")
    }
}