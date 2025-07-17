package usecase

import com.berlin.entity.MediaCast
import repository.MovieDetailsRepository

class GetMovieCastUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long, language: String): List<MediaCast> {
        return movieDetailsRepository.getMovieCastDetails(movieId, language)
    }
}