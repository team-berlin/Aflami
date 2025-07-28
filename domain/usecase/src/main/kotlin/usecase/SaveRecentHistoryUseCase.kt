package usecase

// need to be divided into SaveRecentMoviesHistoryUseCase and SaveRecentTVShowsHistoryUseCase
class SaveRecentHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.saveRecentHistory(query)

}
