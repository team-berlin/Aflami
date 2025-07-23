package usecase

import repository.TvShowDetailsRepository

class GetTvShowDetailsUseCase(
    private val repository: TvShowDetailsRepository
) {
    suspend operator fun invoke(id: Long, language: String) = repository.getTvShowDetails(id, language)
}