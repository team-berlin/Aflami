package usecase

import repository.MovieRepository

class GetPopularTVShowsUseCase(
    private val homeRepository: MovieRepository
) {
    suspend operator fun invoke(language: String): List<Media> =
        homeRepository.getPopularTVShows(language)
}