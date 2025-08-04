package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowDetailsRepository

class GetSimilarTVShowsUseCase(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(tvShowId: Long): List<TVShow> =
        tvShowDetailsRepository.getTVShowsSimilar(tvShowId)
}