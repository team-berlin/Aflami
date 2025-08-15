package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository
import javax.inject.Inject

class GetTVShowGameUseCase @Inject constructor(
    private val getTVShowRepository: TVShowRepository
) {
    suspend operator fun invoke():List<TVShow> = getTVShowRepository.getTVShowGame()
}