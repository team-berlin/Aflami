package usecase

import com.berlin.entity.TVShow
import repository.SearchRepository

class GetSearchTvShowsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, language: String): List<TVShow> =
        searchRepository.searchTVShow(query, language)
}