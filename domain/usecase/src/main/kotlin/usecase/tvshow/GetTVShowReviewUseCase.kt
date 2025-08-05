package usecase.tvshow

import com.berlin.entity.Review
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowReviewUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        tvShowDetailsRepository.getTVShowReviews(id)
}