package usecase.tvshow

import repository.TVShowRepository
import javax.inject.Inject

class ClearTVShowSearchHistoryUseCase @Inject constructor(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke() = repository.clearTVShowSearchHistory()
}