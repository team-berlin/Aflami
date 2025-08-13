package usecase.tvshow

import com.berlin.entity.Video
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowVideos @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
){
    suspend operator fun invoke(id: Long): Video {
        return tvShowDetailsRepository.getTVShowVideos(id)[0]
    }
}