package usecase.mediadetails

import com.berlin.entity.Video
import repository.MovieDetailsRepository

class GetMovieVideos (
    private val movieDetailsRepository: MovieDetailsRepository
){
    suspend operator fun invoke(id: Long): Video{
        return movieDetailsRepository.getMovieVideos(id).first{ it.videoType=="Trailer" }
    }
}