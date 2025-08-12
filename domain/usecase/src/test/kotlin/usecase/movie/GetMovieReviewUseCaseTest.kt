package usecase.movie

import com.berlin.entity.Review
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.MovieDetailsRepository
import usecase.movie.GetMovieGenresUseCaseTest.Companion.GENRES
import kotlin.collections.emptyList

class GetMovieReviewUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieReviewUseCase: GetMovieReviewUseCase =
        GetMovieReviewUseCase(movieDetailsRepository)

    @Test
    fun `should return review related to media id when repository is called`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieReviews(MOVIE_ID) } returns getMovieReview()

        // Act
        val result = getMovieReviewUseCase.invoke(MOVIE_ID)

        // Assert
        assertThat(result).isEqualTo(getMovieReview())
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(MOVIE_ID) }
    }

    @Test
    fun `should return empty list when review is not found`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieReviews(MOVIE_ID) } returns emptyList()

        // Act
        val result = getMovieReviewUseCase(MOVIE_ID)

        // Assert
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(MOVIE_ID) }

    }

    @Test
    fun `should throw exception if movieDetailsRepository throw exception `() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieReviews(MOVIE_ID) } throws Exception()

        // Act
        assertThrows<Exception> { getMovieReviewUseCase.invoke(MOVIE_ID) }

        // Assert
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(MOVIE_ID) }
    }


    private fun getMovieReview(): List<Review> {
        val movieList = mutableListOf<Review>()
        for (i in 0..5) {
            movieList.add(
                Review(
                    id = i.toString(),
                    name = "Manuel São Bento$i",
                    userName = "name$i",
                    avatarImage = "https//:$i",
                    rating = i.toDouble(),
                    content = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this rather disappointingly trundles along for the guts of two hours. It’s starts Read more",
                    date = "20/$i/2000"
                )
            )
        }
        return movieList
    }

    companion object {
        const val MOVIE_ID = 30L
    }
}