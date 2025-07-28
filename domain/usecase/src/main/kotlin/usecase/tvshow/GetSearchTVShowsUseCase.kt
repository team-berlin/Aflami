package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class GetSearchTVShowsUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<TVShow> =
        repository.searchTVShow(query, page)
}