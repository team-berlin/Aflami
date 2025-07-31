package usecase.tvshow

import repository.TVShowRepository

class SaveRecentTVShowsHistoryUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String) =
        repository.saveRecentTVShowsHistory(query)

}