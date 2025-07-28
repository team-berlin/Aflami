package usecase

import com.berlin.entity.TVShow
import repository.TVShowRepository

class GetSearchTvShowsUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<TVShow> =
        repository.searchTVShow(query, page)
}