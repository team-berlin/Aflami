package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowDetailsUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository,
) {
    suspend operator fun invoke(id: Long): TVShow = tvShowDetailsRepository.getTVShowDetails(id)
}