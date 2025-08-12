package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository

class GetTVShowGameUseCase(
    private val getTVShowRepository: TVShowRepository
) {
    suspend operator fun invoke():List<TVShow> = getTVShowRepository.getTVShowGame()
}