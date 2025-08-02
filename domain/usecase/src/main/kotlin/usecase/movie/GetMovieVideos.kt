package usecase.mediadetails

import com.berlin.entity.Video
import repository.MovieDetailsRepository

class GetMovieVideos (
    private val repository: MovieDetailsRepository
){
    suspend operator fun invoke(id: Long): Video{
        return repository.getMovieVideos(id).first{ it.videoType=="Trailer" }
    }

}