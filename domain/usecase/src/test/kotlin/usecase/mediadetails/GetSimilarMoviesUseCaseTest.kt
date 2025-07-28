package usecase.mediadetails

import com.berlin.entity.Movie
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetSimilarMoviesUseCase

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
            movieDetailsRepository.getSimilarMovies(
                mediaId,
            )
        } returns getSimilarMovie()

        //when
        val result = getSimilarMoviesUseCase.invoke(mediaId)
        val expected = getSimilarMovie()

        // then
        Truth.assertThat(result).isEqualTo(expected)
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(mediaId) }
    }

    @Test
    fun `should return empty list when media is not found`() = runTest {
        //given
        val mediaId = 2L
        coEvery {
            movieDetailsRepository.getSimilarMovies(
                mediaId,
            )
        } returns emptyList()

        //when
        val result = getSimilarMoviesUseCase.invoke(mediaId)

        //then
        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getSimilarMovies(mediaId) }

    }
    @Test
    fun `should throw exception if movieDetailsRepository throw exception `()= runTest {
        //give
        val mediaId = 3L
        val exception=Exception()
        coEvery { movieDetailsRepository.getSimilarMovies(
            mediaId,
        ) } throws exception
        //when & then
        assertThrows<Exception> {
            getSimilarMoviesUseCase.invoke(mediaId)
        }
    }


    private fun getSimilarMovie(): List<Movie> {

        val movieList = mutableListOf<Movie>()
        for (i in 0..5) {
            movieList.add(
                Movie(
                    id = TODO(),
                    title = TODO(),
                    rating = TODO(),
                    releaseDate = TODO(),
                    posterURL = TODO(),
                    screenShot = TODO(),
                    description = TODO(),
                    genres = TODO(),
                    duration = TODO(),
                    hasVideo = TODO(),
                    productionCompanies = TODO(),
                    originCountry = TODO(),
                    galleryUrl = TODO()
//                    id = 1L,
//                    title = "Inception$i",
//                    rating = 8.8,
//                    releaseDate = LocalDate(2010, 7, 16),
//                    genres = listOf(28, 12, 878),
//                    poster = "https://poster$i",
//                    screenShot = "https://backdrop$i",
//                    description = "A thief who steals corporate secrets through the use of dream-sharing technology...",
//                    releaseDate = "2010-07-16",
//                    duration = 148
                )
            )
        }
        return movieList
    }
}