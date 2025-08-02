package usecase.mediadetails

import com.berlin.entity.MediaImage
import repository.MovieDetailsRepository

class GetMovieGalleryUseCase(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend operator fun invoke(movieId: Long): MediaImage {
        return movieDetailsRepository.getMovieImages(movieId)
    }
}