package usecase.tvshow

import com.berlin.entity.MediaImage
import repository.TvShowDetailsRepository
import javax.inject.Inject

class GetTVShowGalleryUseCase @Inject constructor(private val tvShowDetailsRepository: TvShowDetailsRepository) {
    suspend operator fun invoke(movieId: Long): MediaImage =
        tvShowDetailsRepository.getSeriesImages(id = movieId)
}