package usecase.tvshow

import repository.TVShowDetailsRepository

class GetTVShowDetailsUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long) = repository.getTVShowDetails(id)
}