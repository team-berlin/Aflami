package usecase.tvshow

import repository.TVShowRepository

class GetRecentTVShowHistoryUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(): List<String> = repository.getRecentTVShowsSearchQueries()
}