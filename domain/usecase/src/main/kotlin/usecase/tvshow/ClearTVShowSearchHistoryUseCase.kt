package usecase.tvshow

import repository.TVShowRepository
import javax.inject.Inject

class ClearTVShowSearchHistoryUseCase @Inject constructor(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke() = tvShowRepository.clearTVShowSearchHistory()
}