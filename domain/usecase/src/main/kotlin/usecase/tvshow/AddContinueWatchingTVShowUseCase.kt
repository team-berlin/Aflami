package usecase.tvshow

import com.berlin.entity.ContinueWatchingMoviesModel
import com.berlin.entity.Movie
import com.berlin.entity.TVShow
import repository.TVShowRepository

class AddContinueWatchingTVShowUseCase(
    private val tVShowRepository: TVShowRepository
) {
    suspend operator fun invoke(tvShow:  TVShow) =
        tVShowRepository.addContinueWatchingTVShow(tvShow)
}