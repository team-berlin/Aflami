package usecase

import repository.MovieRepository

class GetPopularMoviesUseCase(
    private val homeRepository: MovieRepository
) {
    suspend operator fun invoke(language: String): List<Media> =
        homeRepository.getPopularMovies(language)
}
