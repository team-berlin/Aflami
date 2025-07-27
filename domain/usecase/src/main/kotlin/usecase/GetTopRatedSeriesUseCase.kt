package usecase

import com.berlin.entity.TVShow
import repository.HomeRepository

class GetTopRatedSeriesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<TVShow> = homeRepository.getTopRatedSeries(page)
}