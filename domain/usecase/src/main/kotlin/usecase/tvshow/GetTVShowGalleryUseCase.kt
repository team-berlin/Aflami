package usecase.tvshow

import com.berlin.entity.MediaImage
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowGalleryUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): MediaImage =
        tvShowDetailsRepository.getTVShowsImages(id = movieId)
}