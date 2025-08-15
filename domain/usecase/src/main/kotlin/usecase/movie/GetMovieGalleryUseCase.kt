package usecase.movie

import com.berlin.entity.MediaImage
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieGalleryUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): MediaImage {
        return movieDetailsRepository.getMovieImages(movieId)
    }
}