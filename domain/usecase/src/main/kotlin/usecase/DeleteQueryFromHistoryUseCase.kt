package usecase

import repository.SearchRepository

class DeleteQueryFromHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.deleteQueryFromHistory(query)

}
