package usecase.mediadetails

import com.berlin.entity.Review
import repository.TVShowDetailsRepository

class GetSeriesReviewUseCase(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> = tvShowDetailsRepository.getTVShowReviews(id)
}