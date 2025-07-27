package usecase

import com.berlin.entity.Media
import repository.MovieRepository

class GetPopularTVShowsUseCase(
    private val homeRepository: MovieRepository
) {
    suspend operator fun invoke(language: String): List<Media> =
        homeRepository.getPopularTVShows(language)
}