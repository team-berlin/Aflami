package usecase.mediadetails

import com.berlin.entity.Media
import repository.WatchedMediaRepository

class InsertWatchedMediaUseCase (
    private val repository: WatchedMediaRepository
){
    suspend operator fun invoke(media: Media) {
        repository.insertWatchedMedia(media)
    }

}