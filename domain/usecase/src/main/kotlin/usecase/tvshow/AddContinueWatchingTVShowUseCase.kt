package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class AddContinueWatchingTVShowUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(tvShow: TVShow) = repository.addContinueWatchingTVShow(tvShow)
}