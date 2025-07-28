package usecase

// need to be divided into ClearMoviesSearchHistoryUseCase and ClearTVShowSearchHistoryUseCase
class ClearSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.clearSearchHistory()
}
