package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository
import javax.inject.Inject

class GetSearchTVShowsUseCase @Inject constructor(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<TVShow> =
        repository.searchTVShow(query, page)
}