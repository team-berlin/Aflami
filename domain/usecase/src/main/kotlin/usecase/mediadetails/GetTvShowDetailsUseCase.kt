package usecase.mediadetails

import repository.TVShowDetailsRepository

class GetTvShowDetailsUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long, language: String) = repository.getTVShowDetails(id, language)
}