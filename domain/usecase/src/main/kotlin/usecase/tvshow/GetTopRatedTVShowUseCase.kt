package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class GetTopRatedTVShowUseCase(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(page:Int): List<TVShow> = repository.getTopRatedSeries(page)
}