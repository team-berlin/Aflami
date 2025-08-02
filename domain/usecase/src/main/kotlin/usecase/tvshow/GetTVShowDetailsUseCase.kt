package usecase.tvshow

import repository.TVShowRepository

class GetTVShowDetailsUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(id: Long) = repository.getTVShowDetails(id)
}