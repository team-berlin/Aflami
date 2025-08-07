package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class AddContinueWatchingTVShowUseCase(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke(tvShow:  TVShow) =
        tvShowRepository.addContinueWatchingTVShow(tvShow)
}