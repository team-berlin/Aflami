package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TvShowDetailsRepository
import javax.inject.Inject

class GetTVShowDetailsUseCase @Inject constructor(
    private val repository: TvShowDetailsRepository,
) {
    suspend operator fun invoke(id: Long): TVShow = repository.getTvShowDetails(id)
}