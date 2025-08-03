package usecase.tvshow

import repository.TVShowRepository
import javax.inject.Inject

class GetRecentTVShowHistoryUseCase @Inject constructor(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(): List<String> = repository.getRecentTVShowsSearchQueries()
}