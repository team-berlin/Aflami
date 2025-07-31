package usecase.tvshow

import repository.TVShowRepository

class ClearTVShowSearchHistoryUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke() = repository.clearTVShowSearchHistory()
}