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

class GetMovieReviewUseCaseTest {
    private val movieDetailsRepository: MovieDetailsRepository = mockk()
    private val getMovieReviewUseCase: GetMovieReviewUseCase =
        GetMovieReviewUseCase(movieDetailsRepository)

    @Test
    fun `should return review related to media id when repository is called`() = runTest {
        // Arrange
        coEvery { movieDetailsRepository.getMovieReviews(MOVIE_ID) } returns REVIEW

        // Act
        val result = getMovieReviewUseCase.invoke(MOVIE_ID)

        // Assert
        assertThat(result).isEqualTo(REVIEW)
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
        coEvery { movieDetailsRepository.getMovieReviews(MOVIE_ID) } throws
                Exception(ERROR_MESSAGE)

        // Act
        val exception = assertThrows<Exception> { getMovieReviewUseCase.invoke(MOVIE_ID) }

        // Assert
        assertThat(exception.message).isEqualTo(ERROR_MESSAGE)
        coVerify(exactly = 1) { movieDetailsRepository.getMovieReviews(MOVIE_ID) }
    }

    companion object {
        const val ERROR_MESSAGE = "Failed to get movie reviews"
        val REVIEW = listOf(
            Review(
                id = "90",
                name = "Manuel São Bento",
                userName = "name",
                avatarImage = "https//:",
                rating = 9.4,
                content = "Hmmm! I wasn’t sure if I was watching a sentimental edition of “Hawaii Five-O” here or a collection of outtakes from a “Sonic” movie as this rather disappointingly trundles along for the guts of two hours. It’s starts Read more",
                date = "20/2/2000"
            )
        )
        const val MOVIE_ID = 30L
    }
}