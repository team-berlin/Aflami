package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowDetailsRepository

class GetSimilarTVShowsUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(seriesId: Long): List<TVShow> =
        repository.getSimilarTVShows(seriesId)
}