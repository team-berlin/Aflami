package usecase.mediadetails

import com.berlin.entity.Video
import repository.TvShowDetailsRepository

class GetTVShowVideos (
    private val repository: TvShowDetailsRepository
){
    suspend operator fun invoke(id: Long):Video {
        return repository.getTVShowVideos(id).first { it.videoType=="" }
    }
}