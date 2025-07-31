package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class GetPopularTVShowsUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(): List<TVShow> = repository.getPopularTVShows()
}