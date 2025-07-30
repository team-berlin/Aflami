package usecase.movie

import com.berlin.entity.Genre
import io.mockk.mockk
import org.junit.Before
import repository.MovieDetailsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class GetMovieGenresUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private lateinit var getMovieGenresUseCase: GetMovieGenresUseCase

    @Before
    fun setUp() {
        getMovieGenresUseCase = GetMovieGenresUseCase(movieDetailsRepository)
    }

    @Test
    fun `should return list of genres when calling repository`() = runTest {
        coEvery { movieDetailsRepository.getMovieGenres() } returns GENRES

        val callResult = getMovieGenresUseCase()

        assertThat(callResult).isEqualTo(GENRES)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieGenres() }
    }

    @Test
    fun `should throw exception if movieRepository throws exception`() = runTest {
        coEvery { movieDetailsRepository.getMovieGenres() } throws Exception()

        assertThrows<Exception> {
            getMovieGenresUseCase()
        }
    }

    companion object {
        val GENRES = listOf(
            Genre(id = 1, name = "Action"),
            Genre(id = 2, name = "Drama")
        )
    }
}