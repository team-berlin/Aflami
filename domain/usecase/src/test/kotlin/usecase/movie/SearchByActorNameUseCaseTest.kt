package usecase.movie

import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import repository.MovieRepository

class SearchByActorNameUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val searchByActorNameUseCase: SearchByActorNameUseCase =
        SearchByActorNameUseCase(movieRepository)

    @Test
    fun `should return empty list when searching by valid actor name and no media found`() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByActorName(ACTOR_NAME, PAGE) } returns
                emptyList()

        // Act
        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        // Assert
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return list of media related to actor when searching by valid actor name`() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByActorName(ACTOR_NAME, PAGE) } returns MOVIES

        // Act
        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        // Assert
        assertThat(result).hasSize(2)
    }

    @Test
    fun `should return empty list when searching by invalid actor name`() = runTest {
        // Arrange
        coEvery { movieRepository.getMoviesByActorName(ACTOR_NAME, PAGE) } returns
                emptyList()

        // Act
        val result = searchByActorNameUseCase.invoke(ACTOR_NAME, PAGE)

        // Assert
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
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                reviews = emptyList(),
                isFavourite = false,
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
                companyProductions = emptyList(),
                originCountry = "PS",
                galleryUrl = emptyList(),
                reviews = emptyList(),
                isFavourite = false,
            )
        )
        const val ACTOR_NAME = "Tom"
        const val PAGE = 1
    }
}