package usecase.mediadetails

import com.berlin.entity.Video
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieVideos  @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
){
    suspend operator fun invoke(id: Long): Video{
        return movieDetailsRepository.getMovieVideos(id).first{ it.videoType=="Trailer" }
    }
}