package usecase.tvshow

import com.berlin.entity.Video
import repository.TVShowDetailsRepository

class GetTVShowVideos(
    private val tvShowDetailsRepository: TVShowDetailsRepository
){
    suspend operator fun invoke(id: Long): Video {
        return tvShowDetailsRepository.getTVShowVideos(id)[0]
    }
}