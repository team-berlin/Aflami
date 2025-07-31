package usecase.tvshow

import com.berlin.entity.Review
import repository.TVShowDetailsRepository

class GetTVShowReviewUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        repository.getTVShowReviews(id)
}