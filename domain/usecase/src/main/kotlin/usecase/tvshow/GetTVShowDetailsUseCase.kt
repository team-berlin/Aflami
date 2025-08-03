package usecase.tvshow

import repository.TvShowDetailsRepository

class GetTVShowDetailsUseCase(
    private val tvShowDetailsRepository: TvShowDetailsRepository

) {
    suspend operator fun invoke(id: Long) = tvShowDetailsRepository.getTvShowDetails(id)
}