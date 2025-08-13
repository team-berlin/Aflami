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
        val result = getTVShowGenresUseCase()

        // Assert
        assertThat(result).isEqualTo(GENRES)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsGenres() }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsGenres() } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getTVShowGenresUseCase() }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsGenres() }
    }

    companion object {
        const val EXCEPTION = "Error to get genres tv show"
        val GENRES = listOf(
            Genre(id = 1, name = "Drama"),
            Genre(id = 2, name = "Comedy")
        )
    }
}