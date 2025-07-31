package usecase.tvshow

import repository.TVShowDetailsRepository

class GetTVShowGalleryUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<String> =
        repository.getTVShowGallery(id)
}