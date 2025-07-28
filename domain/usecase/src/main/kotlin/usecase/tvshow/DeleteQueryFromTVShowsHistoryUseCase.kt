package usecase.tvshow

import repository.TVShowRepository

class DeleteQueryFromTVShowsHistoryUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String) = repository.deleteTVShowQueryFromHistory(query)
}