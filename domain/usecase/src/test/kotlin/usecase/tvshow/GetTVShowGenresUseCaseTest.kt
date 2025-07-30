package usecase.tvshow

import com.berlin.entity.Genre
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowGenresUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private lateinit var getTVShowGenresUseCase: GetTVShowGenresUseCase

    @Before
    fun setUp() {
        getTVShowGenresUseCase = GetTVShowGenresUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return list of genres when calling repository`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowGenres() } returns GENRES

        val callResult = getTVShowGenresUseCase()

        assertThat(callResult).isEqualTo(GENRES)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowGenres() }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowGenres() } throws Exception()

        assertThrows<Exception> {
            getTVShowGenresUseCase()
        }
    }

    companion object {
        val GENRES = listOf(
            Genre(id = 1, name = "Drama"),
            Genre(id = 2, name = "Comedy")
        )
    }
}