package usecase.tvshow

import repository.TVShowDetailsRepository
import javax.inject.Inject
class GetTVShowVideos @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(id: Long): String? {
        return tvShowDetailsRepository
            .getTVShowVideos(id)
            .firstOrNull { it.videoType == "Trailer" }
            ?.videoUrl

    }
}
