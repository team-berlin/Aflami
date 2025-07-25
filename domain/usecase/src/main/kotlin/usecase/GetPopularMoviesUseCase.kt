package usecase

import com.berlin.entity.Media
import repository.HomeRepository

class GetPopularMoviesUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(language: String): List<Media> =
        homeRepository.getPopularMovies(language)
}
