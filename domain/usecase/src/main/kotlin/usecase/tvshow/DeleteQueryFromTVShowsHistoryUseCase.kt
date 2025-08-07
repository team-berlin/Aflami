package usecase.tvshow

import repository.TVShowRepository
import javax.inject.Inject

class DeleteQueryFromTVShowsHistoryUseCase @Inject constructor(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke(query: String) = tvShowRepository.deleteTVShowQueryFromHistory(query)
}