package usecase.tvshow

import com.berlin.entity.MediaImage
import repository.TvShowDetailsRepository

class GetTVShowGalleryUseCase(private val tvShowDetailsRepository: TvShowDetailsRepository) {
    suspend operator fun invoke(movieId: Long): MediaImage =
        tvShowDetailsRepository.getSeriesImages(id = movieId)
}