package usecase.tvshow

import com.berlin.entity.ContinueWatchingMoviesModel
import repository.TVShowRepository

class AddContinueWatchingTVShowUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(continueWatchingMoviesModel: ContinueWatchingMoviesModel) =
        repository.addContinueWatchingTVShow(continueWatchingMoviesModel)
}