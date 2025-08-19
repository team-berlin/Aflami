package usecase.movie

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
