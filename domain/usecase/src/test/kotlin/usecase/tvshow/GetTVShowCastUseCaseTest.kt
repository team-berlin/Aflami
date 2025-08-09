package usecase.tvshow

import com.berlin.entity.Actor
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowCastUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getTVShowCastUseCase: GetTVShowCastUseCase =
        GetTVShowCastUseCase(tvShowDetailsRepository)

    @Test
    fun `should return list of actors when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsCastDetails(SERIES_ID) } returns ACTORS

        // Act
        getTVShowCastUseCase(SERIES_ID)

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsCastDetails(SERIES_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowsCastDetails(SERIES_ID) } throws Exception()

        // Act
        assertThrows<Exception> { getTVShowCastUseCase(SERIES_ID) }

        // Assert
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowsCastDetails(SERIES_ID) }
    }

    companion object {
        const val SERIES_ID = 123L
        val ACTORS = listOf(
            Actor(id = 1, name = "Ahmed", posterURL = ""),
            Actor(id = 2, name = "Mohammed", posterURL = "")
        )
    }
}