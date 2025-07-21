package usecase

import com.berlin.entity.Movie
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import repository.MovieDetailsRepository

class GetSimilarMoviesUseCaseTest {

    private val movieDetailsRepository = mockk<MovieDetailsRepository>()
    private lateinit var getSimilarMoviesUseCase: GetSimilarMoviesUseCase

    @Before
    fun setUp() {
        getSimilarMoviesUseCase = GetSimilarMoviesUseCase((movieDetailsRepository))
    }

    @Test
    fun `should return media similar to media that returned when repository is called`() = runTest {
        // given
        val mediaId = 0L
        coEvery {
            movieDetailsRepository.getMovieSimilar(
                mediaId,
            )
        } returns getSimilarMovie()

        //when
        val result = getSimilarMoviesUseCase.invoke(mediaId)

        // then
        Truth.assertThat(result).isEqualTo(getSimilarMovie())
        coVerify(exactly = 1) { movieDetailsRepository.getMovieSimilar(mediaId) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery {
            movieDetailsRepository.getMovieSimilar(
                mediaId,
            )
        } returns emptyList()

        //when
        val result = getSimilarMoviesUseCase.invoke(mediaId)

        //then
        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getMovieSimilar(mediaId) }

    }


    private fun getSimilarMovie(): List<Movie> {

        val movieList = mutableListOf<Movie>()
        for (i in 0..5) {
            movieList.add(
                Movie(
                    id = 1L,
                    title = "Inception$i",
                    rating = 8.8,
                    releaseYear = LocalDate(2010, 7, 16),
                    genre = listOf(28, 12, 878),
                    poster = "https://poster$i",
                    backdropPath = "https://backdrop$i",
                    overview = "A thief who steals corporate secrets through the use of dream-sharing technology...",
                    releaseDate = "2010-07-16",
                    runtime = 148
                )
            )
        }
        return movieList
    }


}