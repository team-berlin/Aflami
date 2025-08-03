package usecase.tvshow

import repository.TVShowRepository
import javax.inject.Inject

class DeleteQueryFromTVShowsHistoryUseCase @Inject constructor(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String) = repository.deleteTVShowQueryFromHistory(query)
}