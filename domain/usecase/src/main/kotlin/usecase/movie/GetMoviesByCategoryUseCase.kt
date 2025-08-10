package usecase.movie

import repository.MovieRepository
import javax.inject.Inject

class GetMoviesByCategoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreId: Long, page: Int) =
        movieRepository.getMoviesByCategory(
            genreId = genreId,
            page = page
        )
}