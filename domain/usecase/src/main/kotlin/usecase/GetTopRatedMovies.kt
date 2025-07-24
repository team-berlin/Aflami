package usecase

import repository.HomeRepository
import repository.MovieDetailsRepository

class GetTopRatedMovies(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(page: Int) = homeRepository.getTopRatedMovies(page)
}