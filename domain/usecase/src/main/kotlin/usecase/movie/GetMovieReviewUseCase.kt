package usecase.movie

import com.berlin.entity.Review
import repository.MovieDetailsRepository

class GetMovieReviewUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        repository.getMovieReviews(id)
}