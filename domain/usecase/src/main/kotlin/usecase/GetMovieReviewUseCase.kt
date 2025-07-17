package usecase

import com.berlin.entity.Review
import repository.MovieDetailsRepository

class GetMovieReviewUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> = movieDetailsRepository.getReviews(id)
}