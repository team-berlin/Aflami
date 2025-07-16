package usecase

import repository.SearchRepository

class SaveRecentHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.saveRecentHistory(query)

}
