package usecase.movie

import com.berlin.entity.Actor
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import repository.MovieDetailsRepository

class GetMovieCastUseCaseTest {
    private val repository: MovieDetailsRepository = mockk()
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase

    @Before
    fun setUp() {
        getMovieCastUseCase = GetMovieCastUseCase(repository)
    }

    @Test
    fun `should return list of actors when movieId is valid`() = runTest {

        coEvery { repository.getMovieActors(MOVIE_ID) } returns actors

        val result = getMovieCastUseCase(MOVIE_ID)

        assertEquals(actors, result)

        coVerify(exactly = 1) {
            repository.getMovieActors(MOVIE_ID)
        }
    }

    companion object {
        val actors = listOf(
            Actor(
                id = 90L,
                name = "Nadine Njeim",
                posterURL = "/test.jpg",
            ),
            Actor(
                id = 90L,
                name = "Nadine Al Rassi",
                posterURL = "/test.jpg",
            )
        )
        const val MOVIE_ID = 123L
    }

}