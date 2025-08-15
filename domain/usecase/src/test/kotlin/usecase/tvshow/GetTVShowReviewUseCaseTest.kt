package usecase.tvshow

import com.berlin.entity.Review
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import repository.TVShowDetailsRepository

class GetTVShowReviewUseCaseTest {

    private val tvShowDetailsRepository: TVShowDetailsRepository = mockk()
    private val getTVShowReviewUseCase: GetTVShowReviewUseCase =
        GetTVShowReviewUseCase(tvShowDetailsRepository)


    @Test
    fun `should return list of reviews when calling repository`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowReviews(SERIES_ID) } returns REVIEWS

        // Act
        val result = getTVShowReviewUseCase(SERIES_ID)

        // Assert
        assertThat(result).isEqualTo(REVIEWS)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowReviews(SERIES_ID) }
    }

    @Test
    fun `should throw exception if tvShowDetailsRepository throws exception`() = runTest {
        // Arrange
        coEvery { tvShowDetailsRepository.getTVShowReviews(SERIES_ID) } throws Exception(EXCEPTION)

        // Act
        val exception = assertThrows<Exception> { getTVShowReviewUseCase(SERIES_ID) }

        // Assert
        assertThat(exception.message).isEqualTo(EXCEPTION)
        coVerify(exactly = 1) { tvShowDetailsRepository.getTVShowReviews(SERIES_ID) }
    }

    companion object {
        const val EXCEPTION = "Error to get tv show reviews"
        const val SERIES_ID = 555L
        val REVIEWS = listOf(
            Review(
                id = "1",
                name = "John Doe",
                userName = "johndoe",
                avatarImage = "https://example.com/avatar1.jpg",
                rating = 4.5,
                content = "This TV show was amazing! Great acting and storyline.",
                date = "2025-07-25"
            ),
            Review(
                id = "2",
                name = "Jane Smith",
                userName = "janesmith",
                avatarImage = "https://example.com/avatar2.jpg",
                rating = 3.8,
                content = "Good but a bit slow in the middle episodes.",
                date = "2025-07-26"
            ),
        )
    }
}