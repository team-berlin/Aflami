package usecase.tvshow

import repository.TvShowDetailsRepository

class GetTVShowGalleryUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): List<String> =
        //tvShowDetailsRepository.get(id)
    emptyList()
}