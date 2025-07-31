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
    private lateinit var getTVShowCastUseCase: GetTVShowActorsUseCase

    @Before
    fun setUp() {
        getTVShowCastUseCase = GetTVShowActorsUseCase(tvShowDetailsRepository)
    }

    @Test
    fun `should return list of actors when calling repository`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowActors(SERIES_ID) } returns ACTORS

        val callResult = getTVShowCastUseCase(SERIES_ID)

        assertThat(callResult).isEqualTo(ACTORS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowActors(SERIES_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        coEvery { tvShowDetailsRepository.getTVShowActors(SERIES_ID) } throws Exception()

        assertThrows<Exception> {
            getTVShowCastUseCase(SERIES_ID)
        }
    }

    companion object {
        const val SERIES_ID = 123L
        val ACTORS = listOf(
            Actor(id = 1, name = "Ahmed", posterURL = ""),
            Actor(id = 2, name = "Mohammed", posterURL = "")
        )
    }
}