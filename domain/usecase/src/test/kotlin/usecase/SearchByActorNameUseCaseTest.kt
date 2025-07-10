package usecase

import com.berlin.entity.MediaTypeEntity
import com.berlin.entity.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import repository.SearchRepository


class SearchByActorNameUseCaseTest {

    private val searchRepository = mockk<SearchRepository>()
    private lateinit var searchByActorNameUseCase: SearchByActorNameUseCase

    @Before
    fun setUp() {
        searchByActorNameUseCase = SearchByActorNameUseCase(searchRepository)
    }

    @Test
    fun `When search by valid actor name and no media found, then return empty list`() = runTest {
        // Given
        val actorName = "tom"
        coEvery { searchRepository.searchByActorName(actorName) } returns emptyList()

        // When
        val result = searchByActorNameUseCase.invoke(actorName)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `When search by valid actor name, then return list of media related to actor`() = runTest {
        // Given
        val actorName = "tom"
        coEvery { searchRepository.searchByActorName(actorName) } returns getMediaEntities()

        // When
        val result = searchByActorNameUseCase.invoke(actorName)

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result).hasSize(5)
    }

    @Test
    fun `When search by invalid actor name, then return empty list`() = runTest {
        // Given
        val actorName = "tom"
        coEvery { searchRepository.searchByActorName(actorName) } returns emptyList()

        // When
        val result = searchByActorNameUseCase.invoke(actorName)

        // Then
        assertThat(result).isEmpty()
    }

    private fun getMediaEntities(): List<MediaTypeEntity> {
        return listOf(
            Movie(0, "Movie 1", 7.5, LocalDate(2020, 1, 1), "Des1", emptyList(), "img"),
            Movie(1, "Movie 2", 8.0, LocalDate(2021, 1, 1), "Des2", emptyList(), "img"),
            Movie(2, "Movie 3", 6.5, LocalDate(2022, 1, 1), "Des3", emptyList(), "img"),
            Movie(3, "Movie 4", 9.0, LocalDate(2023, 1, 1), "Des4", emptyList(), "img"),
            Movie(4, "Movie 5", 7.0, LocalDate(2024, 1, 1), "Des5", emptyList(), "poster_e.jpg")
        )
    }
}