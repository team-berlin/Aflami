package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository
import javax.inject.Inject

class AddContinueWatchingTVShowUseCase @Inject constructor(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke(tvShow:  TVShow) =
        tvShowRepository.addContinueWatchingTVShow(tvShow)
}