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
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase =
        GetTVShowGenresUseCase(tvShowDetailsRepository)

    @Test
    fun `should return list of genres when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsGenres() } returns GENRES

        // Act
        getTVShowGenresUseCase()

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsGenres() }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsGenres() } throws Exception()

        // Act
        assertThrows<Exception> { getTVShowGenresUseCase() }

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsGenres() }
    }

    companion object {
        val GENRES = listOf(
            Genre(id = 1, name = "Drama"),
            Genre(id = 2, name = "Comedy")
        )
    }
}