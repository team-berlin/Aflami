package usecase.mediadetails

import com.berlin.entity.TVShow
import repository.ContinueWatchingRepository

class AddContinueWatchingTVShowUseCase (
    private val repository: ContinueWatchingRepository
){
    suspend operator fun invoke(tvShow: TVShow) {
        repository.addContinueWatchingTVShow(tvShow)
    }

}