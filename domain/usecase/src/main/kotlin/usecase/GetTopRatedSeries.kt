package usecase

import com.berlin.entity.TVShow
import repository.HomeRepository

class GetTopRatedSeries(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page:Int): List<TVShow> = homeRepository.getTopRatedSeries(page)
}