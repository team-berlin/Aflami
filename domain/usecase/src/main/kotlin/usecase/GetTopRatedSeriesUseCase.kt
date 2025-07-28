package usecase

import com.berlin.entity.TVShow

class GetTopRatedSeriesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<TVShow> = homeRepository.getTopRatedSeries(page)
}