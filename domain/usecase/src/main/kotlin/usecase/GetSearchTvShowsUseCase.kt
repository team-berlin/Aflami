package usecase


import com.berlin.entity.TvShow
import repository.SearchRepository

class GetSearchTvShowsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<TvShow> = searchRepository.searchTvShow(query)
}