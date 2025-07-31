package usecase.mediadetails

import com.berlin.entity.MediaCast
import repository.MovieDetailsRepository

class GetMovieCastUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<MediaCast> {
        return movieDetailsRepository.getMovieCastDetails(movieId)
    }
}