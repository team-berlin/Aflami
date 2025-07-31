package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class ContinueWatchingTVShowUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(): List<TVShow> = repository.getContinueWatchingTVShows()
}