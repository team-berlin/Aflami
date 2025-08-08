package usecase.tvshow

import com.berlin.entity.PaginatedResult
import com.berlin.entity.RatedMedia
import repository.RatedMediaRepository
import javax.inject.Inject

class GetRatedTVShowsUseCase @Inject constructor(
    private val ratedMediaRepository: RatedMediaRepository
) {
    suspend operator fun invoke(page: Int): PaginatedResult<RatedMedia> {
        return ratedMediaRepository.getRatedTVShows(page)
    }
}