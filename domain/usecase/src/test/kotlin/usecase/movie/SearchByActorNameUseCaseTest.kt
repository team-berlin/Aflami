package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.MovieRepository

class SearchByActorNameUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var searchByActorNameUseCase: SearchByActorNameUseCase

    @Before
    fun setUp() {
        searchByActorNameUseCase = SearchByActorNameUseCase(movieRepository)
    }

    @Test
    fun `When search by valid actor name and no media found, then return empty list`() = runTest {
        coEvery { movieRepository.getMediaByActorName(ACTOR_NAME, PAGE) } returns emptyList()

        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        assertThat(result).isEmpty()
    }

    @Test
    fun `When search by valid actor name, then return list of media related to actor`() = runTest {

        coEvery { movieRepository.getMediaByActorName(ACTOR_NAME, PAGE) } returns MOVIES

        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        assertThat(result).hasSize(2)
    }

    @Test
    fun `When search by invalid actor name, then return empty list`() = runTest {
        coEvery { movieRepository.getMediaByActorName(ACTOR_NAME, PAGE) } returns emptyList()
        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        assertThat(result).isEmpty()
    }

    companion object {
        val MOVIES = listOf(
            Movie(
                id = 90L,
                title = "Test Movie",
                rating = 7.9,
                releaseDate = "1/12/2001",
                posterURL = "/test.jpg",
                screenShot = "/test.jpg",
                description = "This is the test movie",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                productionCompanies = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList()
            ),
            Movie(
                id = 24L,
                title = "Test Movie two",
                rating = 9.7,
                releaseDate = "1/12/2001",
                posterURL = "/test.jpg",
                screenShot = "/test.jpg",
                description = "This is the test movie",
                genres = emptyList(),
                duration = 3,
                hasVideo = false,
                productionCompanies = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList()
            )
        )
        const val ACTOR_NAME = "Tom"
        const val PAGE = 1
    }
}