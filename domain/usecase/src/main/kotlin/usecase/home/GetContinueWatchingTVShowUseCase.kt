package usecase.home

import com.berlin.entity.TVShow
import repository.ContinueWatchingRepository

class GetContinueWatchingTVShowUseCase(
    private val repository: ContinueWatchingRepository
) {
    suspend operator fun invoke(): List<TVShow> {
        return repository.getContinueWatchingTVShows()
    }
}