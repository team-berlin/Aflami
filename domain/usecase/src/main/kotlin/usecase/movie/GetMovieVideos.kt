package usecase.mediadetails

import com.berlin.entity.Video
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieVideos @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): String? {
        return movieDetailsRepository
            .getMovieVideos(id)
            .firstOrNull { it.videoType == "Trailer" }
            ?.videoUrl
    }
}
