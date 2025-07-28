package usecase

// need to be divided into DeleteQueryFromMoviesHistoryUseCase and DeleteQueryFromTVShowsHistoryUseCase
class DeleteQueryFromHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) = searchRepository.deleteQueryFromHistory(query)

}
