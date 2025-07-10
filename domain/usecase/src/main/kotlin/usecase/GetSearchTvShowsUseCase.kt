package usecase


import com.berlin.entity.Television
import repository.SearchRepository

class GetSearchTvShowsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<Television> = searchRepository.searchTvShow(query)
}