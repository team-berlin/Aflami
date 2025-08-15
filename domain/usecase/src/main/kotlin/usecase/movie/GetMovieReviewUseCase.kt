package usecase.movie

import com.berlin.entity.Review
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieReviewUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<Review> =
        movieDetailsRepository.getMovieReviews(id)
}