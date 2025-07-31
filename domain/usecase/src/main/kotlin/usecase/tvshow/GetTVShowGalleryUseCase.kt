package usecase.tvshow

import repository.TVShowDetailsRepository

class GetTVShowGalleryUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(tvShowId: Long): List<String> =
        repository.getTVShowGallery(tvShowId).take(10)
}