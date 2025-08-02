package usecase.mediadetails

import com.berlin.entity.MediaImage
import repository.TvShowDetailsRepository

class GetSeriesGalleryUseCase(private val tvShowDetailsRepository: TvShowDetailsRepository) {
    suspend operator fun invoke(movieId: Long): MediaImage =
        tvShowDetailsRepository.getSeriesImages(id = movieId)
}