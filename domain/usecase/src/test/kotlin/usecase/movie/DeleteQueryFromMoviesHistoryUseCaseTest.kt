package usecase.movie

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieRepository

class DeleteQueryFromMoviesHistoryUseCaseTest {

    private val repository: MovieRepository = mockk(relaxed = true)
    private lateinit var deleteQueryFromMoviesHistoryUseCase: DeleteQueryFromMoviesHistoryUseCase

    @Before
    fun setUp() {
        deleteQueryFromMoviesHistoryUseCase = DeleteQueryFromMoviesHistoryUseCase(repository)
    }

    @Test
    fun `invoke should call deleteQueryFromHistory on repository`() = runTest {
        deleteQueryFromMoviesHistoryUseCase(QUERY)

        coVerify { repository.deleteMovieQueryFromHistory(QUERY) }
    }

    companion object{
        const val QUERY = "Matrix"
    }
}