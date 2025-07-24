package usecase

import repository.HomeRepository

class GetTopRatedMovies(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(page: Int) = homeRepository.getTopRatedMovies(page)
}