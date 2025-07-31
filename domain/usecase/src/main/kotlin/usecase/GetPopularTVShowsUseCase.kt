package usecase

import com.berlin.entity.Media
import repository.MovieRepository

class GetPopularTVShowsUseCase(
    private val homeRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Media> =
        homeRepository.getPopularTVShows()
}