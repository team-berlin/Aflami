package usecase.home

import com.berlin.entity.Media
import repository.WatchedMediaRepository

class GetWatchedMediaUseCase (
    private val repository: WatchedMediaRepository
){
    suspend operator fun invoke(): List<Media> {
        return repository.getWatchedMedia()
    }

}