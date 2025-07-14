package usecase

import repository.SearchRepository

class GetRecentHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): List<String> {
        return searchRepository.getRecentSearchQueries()
    }
}
