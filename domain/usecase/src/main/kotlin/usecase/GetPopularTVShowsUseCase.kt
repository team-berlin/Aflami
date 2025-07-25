package usecase

import com.berlin.entity.Media
import repository.HomeRepository

class GetPopularTVShowsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(language: String): List<Media> =
        homeRepository.getPopularTVShows(language)
}