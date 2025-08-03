package usecase.tvshow

import com.berlin.entity.ContinueWatchingModel
import repository.TVShowRepository

class AddContinueWatchingTVShowUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(continueWatchingModel: ContinueWatchingModel) =
        repository.addContinueWatchingTVShow(continueWatchingModel)
}