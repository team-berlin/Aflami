package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetSimilarTVShowsUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(tvShowId: Long): List<TVShow> =
        tvShowDetailsRepository.getTVShowsSimilar(tvShowId)
}