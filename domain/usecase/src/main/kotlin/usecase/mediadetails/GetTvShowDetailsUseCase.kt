package usecase.mediadetails

import repository.TvShowDetailsRepository

class GetTvShowDetailsUseCase(
    private val repository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long) = repository.getTvShowDetails(id)
}