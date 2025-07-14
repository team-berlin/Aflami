package usecase

import repository.SearchRepository

class ClearSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() {
        return searchRepository.clearSearchHistory()
    }
}
