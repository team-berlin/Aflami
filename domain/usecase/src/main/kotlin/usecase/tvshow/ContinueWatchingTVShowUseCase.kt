package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository
import javax.inject.Inject

class ContinueWatchingTVShowUseCase @Inject constructor(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke(page:Int): List<TVShow> = tvShowRepository.getContinueWatchingTVShows(page)
}