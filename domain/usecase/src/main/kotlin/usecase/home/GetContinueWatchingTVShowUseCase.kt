package usecase.home

import com.berlin.entity.TVShow
import repository.ContinueWatchingRepository

class GetContinueWatchingTVShowUseCase(
    private val repository: ContinueWatchingRepository
) {
    suspend operator fun invoke(page: Int): List<TVShow> {
        return repository.getContinueWatchingTVShows(page)
    }
}