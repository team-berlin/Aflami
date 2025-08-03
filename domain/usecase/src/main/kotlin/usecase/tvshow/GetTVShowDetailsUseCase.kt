package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TvShowDetailsRepository

class GetTVShowDetailsUseCase(
    private val repository: TvShowDetailsRepository,
) {
    suspend operator fun invoke(id: Long): TVShow = repository.getTvShowDetails(id)
}