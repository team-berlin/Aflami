package usecase

// need to be divided into GetRecentMoviesHistoryUseCase and GetRecentTVShowHistoryUseCase
class GetRecentHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): List<String> = searchRepository.getRecentSearchQueries()
}
